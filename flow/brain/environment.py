import gym
import matplotlib
import cv2
import base64
import math
from pupil_apriltags import Detector
from PIL import Image as im

from collector.data_collector import DataCollector

matplotlib.use('TkAgg')
from gym import spaces
import numpy as np
import matplotlib.pyplot as plt


def decode_image_from_esp32(image):
    esp32_image = np.frombuffer(base64.b64decode(image), np.uint8)
    return cv2.imdecode(esp32_image, cv2.IMREAD_GRAYSCALE)


class CustomLayout(object):

    def __init__(self, length, height):
        self.length = length
        self.height = height
        self.layout = np.zeros((self.length, self.height))
        self.obstacles = []
        self.robot_position = []
        # make targets an object that holds the relative positions of AprilTags and their actual image
        self.targets = []
        self.apriltag_detected = False
        self.obstacle_detected = False

    def _create_layout(self, start_coord, end_coord, layout_type):
        start_row, start_col = start_coord
        end_row, end_col = end_coord

        if start_row < 0 or start_col < 0 or end_row >= self.layout.shape[0] or end_col >= self.layout.shape[1]:
            print("Coordinates out of bounds.")

        self.layout[start_row:end_row + 1, start_col:end_col + 1] = 1

        if layout_type == 'O':
            for row in range(start_row, end_row + 1):
                for col in range(start_col, end_col + 1):
                    self.obstacles.append((row, col))
        elif layout_type == 'T':
            for row in range(start_row, end_row + 1):
                for col in range(start_col, end_col + 1):
                    self.targets.append((row, col))

    def generate_obstacles(self):
        self._create_layout([50, 0], [50, 999], 'O')
        self._create_layout([300, 0], [300, 120], 'O')
        self._create_layout([300, 120], [700, 120], 'O')
        self._create_layout([700, 0], [700, 120], 'O')
        self._create_layout([450, 500], [450, 700], 'O')
        self._create_layout([450, 500], [750, 500], 'O')
        self._create_layout([750, 500], [750, 700], 'O')
        self._create_layout([450, 700], [750, 700], 'O')
        self._create_layout([650, 780], [999, 780], 'O')
        self._create_layout([999, 780], [999, 999], 'O')
        self._create_layout([650, 999], [999, 999], 'O')
        self._create_layout([650, 780], [650, 999], 'O')
        self._create_layout([800, 0], [800, 350], 'O')
        self._create_layout([800, 350], [999, 350], 'O')

    def generate_targets(self):
        # self._create_layout([750, 150], [799, 150], 'T')
        # self._create_layout([750, 150], [750, 200], 'T')
        # self._create_layout([750, 200], [799, 200], 'T')
        for x in range(100, 150):
            for y in range(150, 200):
                self.targets.append([x, y])


class RobotEnv(gym.Env):
    def __init__(self):
        super(RobotEnv, self).__init__()
        # Train option
        self.is_training = True
        # Collect data
        self.data_collector = DataCollector()
        self.index = 0
        # Define action and observation space
        self.custom_layout = CustomLayout(1000, 1000)
        self.action_space = spaces.Discrete(4)  # Four discrete actions: forward, backward, left, right
        self.observation_space = spaces.MultiBinary(
            self.custom_layout.length * self.custom_layout.height)  # Four ultrasonic sensor readings + AprilTag detection
        # Initialize robot's position and sensors
        self.custom_layout.robot_position = [100, 800]  # Robot's initial position (x, y)
        # Target positions (x, y)
        self.custom_layout.generate_targets()  # AprilTag position (x, y)
        # Obstacles positions
        self.custom_layout.generate_obstacles()
        # Initial orientation
        self.orientation = 0;

    def reset(self, **kwargs):
        # if self.is_training:
        #     if len(self.data_collector.ultrasonic_data) != 1000:
        #         self.data_collector.get_data()
        #     # Reset robot's position
        #     self.index = 0
        self.custom_layout.robot_position = [100, 800]
        return self._get_observation()

    def _get_min_distance_from_layout_obstacles(self):
        distances = []
        for obs_pos in self.custom_layout.obstacles:
            distances.append(math.dist(obs_pos, self.custom_layout.robot_position))
        return distances

    def step(self, action):
        # Update robot's position based on action
        # Update robot's position and orientation based on action
        if action == 0:  # Move forward
            if self.orientation == 0:  # Facing up
                self.custom_layout.robot_position[1] -= 50
            elif self.orientation == 1:  # Facing right
                self.custom_layout.robot_position[0] += 50
            elif self.orientation == 2:  # Facing down
                self.custom_layout.robot_position[1] += 50
            elif self.orientation == 3:  # Facing left
                self.custom_layout.robot_position[0] -= 50
        elif action == 1:  # Move backward
            if self.orientation == 0:  # Facing up
                self.custom_layout.robot_position[1] += 50
            elif self.orientation == 1:  # Facing right
                self.custom_layout.robot_position[0] -= 50
            elif self.orientation == 2:  # Facing down
                self.custom_layout.robot_position[1] -= 50
            elif self.orientation == 3:  # Facing left
                self.custom_layout.robot_position[0] += 50
        elif action == 2:  # Turn left
            # Move left in the matrix
            if self.orientation == 0:
                self.custom_layout.robot_position[0] -= 50
            elif self.orientation == 1:
                self.custom_layout.robot_position[1] -= 50
            elif self.orientation == 2:
                self.custom_layout.robot_position[0] += 50
            elif self.orientation == 3:
                self.custom_layout.robot_position[1] += 50
            # Update orientation
            self.orientation = (self.orientation - 1) % 4
        elif action == 3:  # Turn right
            # Move right in the matrix
            if self.orientation == 0:
                self.custom_layout.robot_position[0] += 50
            elif self.orientation == 1:
                self.custom_layout.robot_position[1] += 50
            elif self.orientation == 2:
                self.custom_layout.robot_position[0] -= 50
            elif self.orientation == 3:
                self.custom_layout.robot_position[1] -= 50
            # Update orientation
            self.orientation = (self.orientation + 1) % 4

        self.custom_layout.robot_position[0] = min(max(self.custom_layout.robot_position[0], 0), 1000)
        self.custom_layout.robot_position[1] = min(max(self.custom_layout.robot_position[1], 0), 1000)

        if self.is_training:
            # Return new observation flag
            observation = self._get_observation()

            # Check if robot has collided with an obstacle or with the layout
            reward = 0
            if self.custom_layout.obstacle_detected or min(self._get_min_distance_from_layout_obstacles()) < 15:
                reward -= -100

            # Check if robot has reached AprilTag
            reached_target = False
            if self.custom_layout.robot_position in self.custom_layout.targets:
                reached_target = True
                reward += 10  # Positive reward for reaching AprilTag

            # Check if robot detected AprilTag
            if self.custom_layout.apriltag_detected:
                reward += 5

            return observation, reward, reached_target
        else:
            return self.custom_layout.robot_position

    def _get_observation(self):
        try:
            # # Use actual data from ultrasonic to avoid dynamic obstacles
            #
            # if self.data_collector.ultrasonic_data[self.index] <= 15:
            #     self.custom_layout.obstacle_detected = True
            # else:
            #     self.custom_layout.obstacle_detected = False
            #
            # # Use the actual robot to retrieve and compare if he got to see an actual apriltag
            #
            # image_decoded = decode_image_from_esp32(self.data_collector.esp32_data[self.index])
            # try:
            #     if len(Detector().detect(image_decoded)) != 0:
            #         self.custom_layout.apriltag_detected = True
            #     else:
            #         self.custom_layout.apriltag_detected = False
            # except:
            #     self.custom_layout.apriltag_detected = False
            #
            # self.index = self.index + 1

            return self.custom_layout.robot_position
        except:
            return self.custom_layout.robot_position

    @staticmethod
    def save(Q_table):
        np.save(file="q_table.npy", arr=Q_table)

    @staticmethod
    def load():
        return np.load(file="q_table.npy")

    def render(self):
        plt.figure()
        plt.imshow(im.fromarray(self.custom_layout.layout))  # Initialize empty plot

        # Plot obstacles (red)
        for obstacle_pos in self.custom_layout.obstacles:
            plt.scatter(obstacle_pos[0], obstacle_pos[1], color='red')

        # Plot robot (blue)
        plt.scatter(self.custom_layout.robot_position[0], self.custom_layout.robot_position[1], color='blue')

        # Plot target (green)
        for target_pos in self.custom_layout.targets:
            plt.scatter(target_pos[0], target_pos[1], color='green')
        plt.legend(['Obstacles', 'Robot', 'Target'])
        plt.title('Robot Environment')
        plt.show()
