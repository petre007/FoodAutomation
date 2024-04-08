import gym
from gym import spaces
import numpy as np


class RobotEnv(gym.Env):
    def __init__(self):
        super(RobotEnv, self).__init__()

        # Define action and observation space
        self.action_space = spaces.Discrete(4)  # Four discrete actions: forward, backward, left, right
        self.observation_space = spaces.Box(low=0, high=100, shape=(5,),
                                            dtype=np.float32)  # Four ultrasonic sensor readings + AprilTag detection

        # Initialize robot's position and sensors
        self.robot_position = [0, 0]  # Robot's initial position (x, y)
        self.obstacle_positions = [[3, 3], [5, 7], [8, 1]]  # Example obstacle positions (x, y)
        self.apriltag_position = [9, 9]  # AprilTag position (x, y)

    def reset(self, **kwargs):
        # Reset robot's position and return initial sensor readings
        self.robot_position = [0, 0]
        return self._get_observation()

    def step(self, action):
        # Update robot's position based on action
        if action == 0:  # Move forward
            self.robot_position[1] += 1
        elif action == 1:  # Move backward
            self.robot_position[1] -= 1
        elif action == 2:  # Move left
            self.robot_position[0] -= 1
        elif action == 3:  # Move right
            self.robot_position[0] += 1

        # Clip robot's position to stay within the environment bounds
        self.robot_position = np.clip(self.robot_position, 0, 9)

        # Check if robot has collided with an obstacle
        reward = 0
        for obstacle_pos in self.obstacle_positions:
            if np.array_equal(self.robot_position, obstacle_pos):
                reward = -1  # Negative reward for collision
                break

        # Check if robot has reached AprilTag
        if np.array_equal(self.robot_position, self.apriltag_position):
            reward = 10  # Positive reward for reaching AprilTag

        # Return new observation, reward, and done flag
        observation = self._get_observation()
        done = np.array_equal(self.robot_position, self.apriltag_position)  # Episode ends when robot reaches AprilTag
        return observation, reward, done, {}

    def _get_observation(self):
        # Simulate ultrasonic sensor readings based on robot's position and obstacle positions
        sensor_readings = []
        for obstacle_pos in self.obstacle_positions:
            distance = np.sqrt(
                (self.robot_position[0] - obstacle_pos[0]) ** 2 + (self.robot_position[1] - obstacle_pos[1]) ** 2)
            sensor_readings.append(distance)

        # Simulate AprilTag detection
        apriltag_detected = int(np.array_equal(self.robot_position, self.apriltag_position))

        return np.array(sensor_readings + [apriltag_detected])

    def render(self, mode='human'):
        # Render the environment (optional)
        pass
