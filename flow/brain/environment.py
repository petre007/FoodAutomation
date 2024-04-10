import gym
import matplotlib
import cv2
import base64
import math
from pupil_apriltags import Detector

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

    def _create_obstacles(self, start_coord, end_coord):
        start_row, start_col = start_coord
        end_row, end_col = end_coord

        if start_row < 0 or start_col < 0 or end_row >= self.layout.shape[0] or end_col >= self.layout.shape[1]:
            print("Coordinates out of bounds.")

        self.layout[start_row:end_row + 1, start_col:end_col + 1] = 1

        for row in range(start_row, end_row + 1):
            for col in range(start_col, end_col + 1):
                self.obstacles.append((row, col))

    def generate_obstacles(self):
        self._create_obstacles([50, 0], [50, 999])
        self._create_obstacles([300, 0], [300, 40])
        self._create_obstacles([300, 40], [700, 40])
        self._create_obstacles([700, 0], [700, 40])
        self._create_obstacles([450, 500], [450, 700])
        self._create_obstacles([450, 500], [750, 500])
        self._create_obstacles([750, 500], [750, 700])
        self._create_obstacles([450, 700], [750, 700])


class RobotEnv(gym.Env):
    def __init__(self):
        super(RobotEnv, self).__init__()
        self.data_collector = DataCollector()
        # Define action and observation space
        self.action_space = spaces.Discrete(4)  # Four discrete actions: forward, backward, left, right
        self.observation_space = spaces.Box(low=0, high=100, shape=(1000,),
                                            dtype=np.float32)  # Four ultrasonic sensor readings + AprilTag detection
        self.custom_layout = CustomLayout(1000, 1000)
        # Initialize robot's position and sensors
        self.custom_layout.robot_position = [100, 800]  # Robot's initial position (x, y)
        # Target positions (x, y)
        self.custom_layout.targets = [600, 300]  # AprilTag position (x, y)
        # Obstacles positions
        self.custom_layout.generate_obstacles()
        # Detection of apriltag

    def reset(self, **kwargs):
        # Reset robot's position and return initial sensor readings
        self.custom_layout.robot_position = [100, 800]
        return self._get_observation()

    def _get_min_distance_from_layout_obstacles(self):
        distances = []
        for obs_pos in self.custom_layout.obstacles:
            distances.append(math.dist(obs_pos, self.custom_layout.robot_position))
        return distances

    def step(self, action):
        # Update robot's position based on action
        if action == 0:  # Move forward
            self.custom_layout.robot_position[1] += 1
        elif action == 1:  # Move backward
            self.custom_layout.robot_position[1] -= 1
        elif action == 2:  # Move left
            self.custom_layout.robot_position[0] -= 1
        elif action == 3:  # Move right
            self.custom_layout.robot_position[0] += 1

        # Clip robot's position to stay within the environment bounds
        self.custom_layout.robot_position = np.clip(self.custom_layout.robot_position,
                                                    self.custom_layout.height,
                                                    self.custom_layout.length)

        # Check if robot has collided with an obstacle or with the layout
        reward = 0
        if self.custom_layout.obstacle_detected or min(self._get_min_distance_from_layout_obstacles()) < 15:
            reward -= -1

        # Check if robot has reached AprilTag
        if np.array_equal(self.custom_layout.robot_position, self.custom_layout.targets):
            reward += 10  # Positive reward for reaching AprilTag

        # Check if robot detected AprilTag
        if self.custom_layout.apriltag_detected:
            reward += 5

        # Return new observation, reward, and done flag
        observation = self._get_observation()
        return observation, reward

    def _get_observation(self):
        # Use actual data from ultrasonic to avoid dynamic obstacles

        sensor_readings = self.data_collector.ultrasonic_data
        if min(sensor_readings) <= 15:
            self.custom_layout.obstacle_detected = True
        else:
            self.custom_layout.obstacle_detected = False

        # Use the actual robot to retrieve and compare if he got to see an actual apriltag
        image_readings = self.data_collector.esp32_data
        for image in image_readings:
            image_decoded = decode_image_from_esp32(image)
            if len(Detector().detect(image_decoded)) != 0:
                self.custom_layout.apriltag_detected = True
                break
            else:
                self.custom_layout.apriltag_detected = False

        reached_target = int(np.array_equal(self.custom_layout.robot_position, self.custom_layout.targets))

        return reached_target, self.custom_layout.obstacle_detected, self.custom_layout.apriltag_detected

    def render(self):
        plt.figure()
        plt.imshow(self.custom_layout.layout)  # Initialize empty plot

        # Plot obstacles (red)
        for obstacle_pos in self.custom_layout.obstacles:
            plt.scatter(obstacle_pos[0], obstacle_pos[1], color='red')

        # Plot robot (blue)
        plt.scatter(self.custom_layout.robot_position[0], self.custom_layout.robot_position[1], color='blue')

        # Plot target (green)
        plt.scatter(self.custom_layout.targets[0], self.custom_layout.targets[1], color='green')
        plt.legend(['Obstacles', 'Robot', 'Target'])
        plt.title('Robot Environment')
        plt.show()
