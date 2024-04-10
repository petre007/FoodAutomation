import os
import random
from keras.src.layers import Dense, Conv2D, Flatten, concatenate
from collections import deque
from brain.environment import RobotEnv

os.environ["KERAS_BACKEND"] = "tensorflow"

from keras import Sequential, Model
import numpy as np


def create_obstacle_avoidance_model(input_shape):
    model = Sequential([
        Dense(64, input_shape=input_shape, activation='relu'),
        Dense(32, activation='relu'),
        Dense(1, activation='sigmoid')  # Output layer with a single unit and sigmoid activation
    ])
    model.compile(optimizer='adam', loss='binary_crossentropy', metrics=['accuracy'])
    return model


def create_image_model(input_shape):
    model = Sequential([
        Conv2D(32, (3, 3), activation='relu', input_shape=input_shape),
        Conv2D(64, (3, 3), activation='relu'),
        Flatten(),
        Dense(128, activation='relu'),
        Dense(4, activation='softmax')
    ])
    model.compile(optimizer='adam', loss='categorical_crossentropy', metrics=['accuracy'])
    return model


def combine_models(obstacle_avoidance_model, image_model):
    # Create obstacle avoidance model for sensor data
    sensor_model = create_obstacle_avoidance_model(obstacle_avoidance_model)

    # Create image model for image data
    image_model = create_image_model(image_model)

    # Combine both models
    combined_input = concatenate([sensor_model.output, image_model.output])
    combined_output = Dense(4, activation='softmax')(combined_input)  # Output layer for Left, Right, Forward, Back
    combined_model = Model(inputs=[sensor_model.input, image_model.input], outputs=combined_output)

    # Compile the combined model
    combined_model.compile(optimizer='adam', loss='categorical_crossentropy', metrics=['accuracy'])

    return combined_model


def train_model(model):
    # Create an instance of the environment
    env = RobotEnv()

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

        actions_taken = []  # List to store actions taken in each episode

        for step in range(max_steps_per_episode):
            # Choose action using epsilon-greedy policy
            if np.random.rand() <= epsilon:
                action = env.action_space.sample()  # Explore
            else:
                # Predict the probabilities of each action
                action_probabilities = model.predict(obs)
                # Choose action with highest probability
                action = np.argmax(action_probabilities)

            # Take action in the environment
            next_obs, reward = env.step(action)
            next_obs = np.reshape(next_obs, [1, -1])

            # Store transition in replay memory
            memory.append((obs, action, reward, next_obs))

            total_reward += reward
            obs = next_obs

            actions_taken.append(action)  # Store the action taken

            if next_obs[1]:
                break

        # Experience replay
        if len(memory) >= batch_size:
            minibatch = random.sample(list(memory), batch_size)  # Convert deque to list for random sampling
            obs_batch, action_batch, reward_batch, next_obs_batch, done_batch = zip(*minibatch)
            obs_batch = np.concatenate(obs_batch)
            next_obs_batch = np.concatenate(next_obs_batch)

            target_batch = np.array(action_batch)  # Target is the action taken
            model.fit(obs_batch, target_batch, epochs=1, verbose=0)

        # Decay epsilon
        if epsilon > epsilon_min:
            epsilon *= epsilon_decay

        # Print episode info
        print(f"Episode: {episode + 1}/{num_episodes}, Total Reward: {total_reward}, Epsilon: {epsilon}")

    # Close the environment
    env.close()
