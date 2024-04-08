import gym
import matplotlib

matplotlib.use('TkAgg')
from gym import spaces
import numpy as np
import matplotlib.pyplot as plt


class CustomLayout(object):

    def __init__(self, length, height):
        self.length = length
        self.height = height
        self.layout = np.zeros((self.length, self.height))
        self.obstacles = []
        self.robot_position = []
        self.targets = []

    def create_obstacles(self, start_coord, end_coord):
        start_row, start_col = start_coord
        end_row, end_col = end_coord

        if start_row < 0 or start_col < 0 or end_row >= self.layout.shape[0] or end_col >= self.layout.shape[1]:
            print("Coordinates out of bounds.")

        self.layout[start_row:end_row + 1, start_col:end_col + 1] = 1

        for row in range(start_row, end_row + 1):
            for col in range(start_col, end_col + 1):
                self.obstacles.append((row, col))


class RobotEnv(gym.Env):
    def __init__(self):
        super(RobotEnv, self).__init__()

        # Define action and observation space
        self.action_space = spaces.Discrete(4)  # Four discrete actions: forward, backward, left, right
        self.observation_space = spaces.Box(low=0, high=100, shape=(5,),
                                            dtype=np.float32)  # Four ultrasonic sensor readings + AprilTag detection
        self.custom_layout = CustomLayout(1000, 1000)
        # Initialize robot's position and sensors
        self.custom_layout.robot_position = [100, 800]  # Robot's initial position (x, y)
        # Example obstacle positions (x, y)
        self.custom_layout.targets = [600, 300]  # AprilTag position (x, y)

        self.custom_layout.create_obstacles([50, 0], [50, 999])
        self.custom_layout.create_obstacles([300, 0], [300, 40])
        self.custom_layout.create_obstacles([300, 40], [700, 40])
        self.custom_layout.create_obstacles([700, 0], [700, 40])
        self.custom_layout.create_obstacles([450, 500], [450, 700])
        self.custom_layout.create_obstacles([450, 500], [750, 500])
        self.custom_layout.create_obstacles([750, 500], [750, 700])
        self.custom_layout.create_obstacles([450, 700], [750, 700])

    def reset(self, **kwargs):
        # Reset robot's position and return initial sensor readings
        self.custom_layout.robot_position = [100, 800]
        return self._get_observation()

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
        self.custom_layout.robot_position = np.clip(self.custom_layout.robot_position, self.custom_layout.height, self.custom_layout.length)

        # Check if robot has collided with an obstacle
        reward = 0
        for obstacle_pos in self.custom_layout.obstacles:
            if np.array_equal(self.custom_layout.robot_position, obstacle_pos):
                reward = -1  # Negative reward for collision
                break

        # Check if robot has reached AprilTag
        if np.array_equal(self.custom_layout.robot_position, self.custom_layout.targets):
            reward = 10  # Positive reward for reaching AprilTag

        # Return new observation, reward, and done flag
        observation = self._get_observation()
        done = np.array_equal(self.custom_layout.robot_position,
                              self.custom_layout.targets)  # Episode ends when robot reaches AprilTag
        return observation, reward, done, {}

    def _get_observation(self):
        # Simulate ultrasonic sensor readings based on robot's position and obstacle positions
        sensor_readings = []
        for obstacle_pos in self.custom_layout.obstacles:
            distance = np.sqrt(
                (self.custom_layout.robot_position[0] - obstacle_pos[0]) ** 2 + (
                            self.custom_layout.robot_position[1] - obstacle_pos[1]) ** 2)
            sensor_readings.append(distance)

        # Simulate AprilTag detection
        apriltag_detected = int(np.array_equal(self.custom_layout.robot_position, self.custom_layout.targets))

        return np.array(sensor_readings + [apriltag_detected])

    def render(self, mode='human'):
        plt.figure()
        plt.imshow(self.custom_layout.layout)  # Initialize empty plot

        # Plot obstacles (red)
        for obstacle_pos in self.custom_layout.obstacles:
            plt.scatter(obstacle_pos[0], obstacle_pos[1], color='red')

        # Plot robot (blue)
        plt.scatter(self.custom_layout.robot_position[0], self.custom_layout.robot_position[1], color='blue')

        # Plot target (green)
        plt.scatter(self.custom_layout.targets[0], self.custom_layout.targets[1], color='green')

        plt.title('Robot Environment')
        plt.show()
