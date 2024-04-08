import os
import random
from keras.src.layers import Dense, Conv2D, Flatten
from collections import deque
from brain.environment import RobotEnv
from collector.data_collector import DataCollector

os.environ["KERAS_BACKEND"] = "tensorflow"

import cv2
from keras import Sequential
import numpy as np


def decode_image_from_esp32():
    data_collector = DataCollector()
    esp32_image_base64 = data_collector.esp32_data
    esp32_images = []
    for image in esp32_image_base64:
        esp32_images = np.fromstring(image.decode('base64'), np.uint8)
    return cv2.imdecode(esp32_images, cv2.IMREAD_ANYCOLOR)


def create_obstacle_avoidance_model(input_shape):
    model = Sequential([
        Dense(64, input_shape=input_shape, activation='relu'),
        Dense(32, activation='relu'),
        Dense(1, activation='sigmoid')  # Output layer with a single unit and sigmoid activation
    ])
    model.compile(optimizer='adam', loss='binary_crossentropy', metrics=['accuracy'])
    return model


def train_obstacle_avoidance_model(model):
    env = RobotEnv()  # Create an instance of the environment

    # Define training parameters
    num_episodes = 1000
    max_steps_per_episode = 100
    epsilon = 1.0  # Exploration rate
    epsilon_min = 0.01
    epsilon_decay = 0.995
    batch_size = 32
    memory = deque(maxlen=2000)  # Replay memory

    for episode in range(num_episodes):
        obs = env.reset()  # Reset the environment at the beginning of each episode
        obs = np.reshape(obs, [1, -1])
        total_reward = 0

        for step in range(max_steps_per_episode):
            # Choose action using epsilon-greedy policy
            if np.random.rand() <= epsilon:
                action = env.action_space.sample()  # Explore
            else:
                # Predict the probability of needing to "Stop"
                probability_stop = model.predict(obs)[0][0]
                # Convert probability to action (0 for "Go", 1 for "Stop")
                action = 1 if probability_stop > 0.5 else 0

            # Take action in the environment
            next_obs, reward, done, _ = env.step(action)
            next_obs = np.reshape(next_obs, [1, -1])

            # Store transition in replay memory
            memory.append((obs, action, reward, next_obs, done))

            total_reward += reward
            obs = next_obs

            if done:
                break

        # Experience replay
        if len(memory) >= batch_size:
            minibatch = random.sample(list(memory), batch_size)  # Convert deque to list for random sampling
            obs_batch, action_batch, reward_batch, next_obs_batch, done_batch = zip(*minibatch)
            obs_batch = np.concatenate(obs_batch)
            next_obs_batch = np.concatenate(next_obs_batch)

            target_batch = np.array(action_batch)  # Target is the action taken (0 or 1)
            model.fit(obs_batch, target_batch, epochs=1, verbose=0)

        # Decay epsilon
        if epsilon > epsilon_min:
            epsilon *= epsilon_decay

        # Print episode info
        print(f"Episode: {episode + 1}/{num_episodes}, Total Reward: {total_reward}, Epsilon: {epsilon}")

    # Close the environment
    env.close()


def create_image_model(input_shape):
    model = Sequential([
        Conv2D(32, (3, 3), activation='relu', input_shape=input_shape),
        Conv2D(64, (3, 3), activation='relu'),
        Flatten(),
        Dense(128, activation='relu'),
        Dense(4, activation='softmax')  # Output layer with 4 units for LEFT, RIGHT, FORWARD, BACK
    ])
    model.compile(optimizer='adam', loss='categorical_crossentropy', metrics=['accuracy'])
    return model


def train_image_model(model):
    env = RobotEnv()  # Create an instance of the environment

    # Define training parameters
    num_episodes = 1000
    max_steps_per_episode = 100
    epsilon = 1.0  # Exploration rate
    epsilon_min = 0.01
    epsilon_decay = 0.995
    batch_size = 32
    memory = deque(maxlen=2000)  # Replay memory

    for episode in range(num_episodes):
        obs = env.reset()  # Reset the environment at the beginning of each episode
        total_reward = 0

        for step in range(max_steps_per_episode):
            # Choose action using epsilon-greedy policy
            if np.random.rand() <= epsilon:
                action = env.action_space.sample()  # Explore
            else:
                # Predict the probabilities of each action
                action_probabilities = model.predict(obs[np.newaxis, :])
                # Choose action with highest probability
                action = np.argmax(action_probabilities)

            # Take action in the environment
            next_obs, reward, done, _ = env.step(action)

            # Store transition in replay memory
            memory.append((obs, action, reward, next_obs, done))

            total_reward += reward
            obs = next_obs

            if done:
                break

        # Experience replay
        if len(memory) >= batch_size:
            minibatch = random.sample(memory, batch_size)  # Sample minibatch from replay memory
            obs_batch, action_batch, reward_batch, next_obs_batch, done_batch = zip(*minibatch)
            obs_batch = np.stack(obs_batch)
            next_obs_batch = np.stack(next_obs_batch)

            target_batch = np.array(action_batch)  # Target is the action taken
            model.fit(obs_batch, target_batch, epochs=1, verbose=0)

        # Decay epsilon
        if epsilon > epsilon_min:
            epsilon *= epsilon_decay

        # Print episode info
        print(f"Episode: {episode + 1}/{num_episodes}, Total Reward: {total_reward}, Epsilon: {epsilon}")

    # Close the environment
    env.close()
