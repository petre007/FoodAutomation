import os

from brain.environment import RobotEnv
from collector.data_collector import DataCollector

os.environ["KERAS_BACKEND"] = "tensorflow"

import numpy as np


def rl_model_train():
    # Instantiate environment and agent
    env = RobotEnv()
    state_size = env.observation_space.n
    action_size = env.action_space.n
    Q_table = np.zeros((state_size, action_size))
    # number of episode we will run
    n_episodes = 1000  # collect 1000 elements for each input (ultrasonic and esp32)

    # maximum of iteration per episode
    max_iter_episode = 100

    # initialize the exploration probability to 1
    exploration_proba = 1

    # exploartion decreasing decay for exponential decreasing
    exploration_decreasing_decay = 0.001

    # minimum of exploration proba
    min_exploration_proba = 0.01

    # discounted factor
    gamma = 0.99

    # learning rate
    lr = 0.1

    # we iterate over episodes
    for e in range(n_episodes):

        # we initialize the first state of the episode
        current_state = env.reset()

        # sum the rewards that the agent gets from the environment
        total_episode_reward = 0

        total_rewards_episode = list()

        for i in range(max_iter_episode):
            # we sample a float from a uniform distribution over 0 and 1
            # if the sampled flaot is less than the exploration proba
            #     the agent selects arandom action
            # else
            #     he exploits his knowledge using the bellman equation

            if np.random.uniform(0, 1) < exploration_proba:
                action = env.action_space.sample()
            else:
                action = np.argmax(Q_table[current_state, :])

            # The environment runs the chosen action and returns
            # the next state, a reward and true if the epiosed is ended.
            next_state, reward, done = env.step(action)

            # We update our Q-table using the Q-learning iteration
            Q_table[current_state, action] = (1 - lr) * Q_table[current_state, action] + lr * (
                    reward + gamma * max(Q_table[next_state, :]))
            total_episode_reward = total_episode_reward + reward
            # If the episode is finished, we leave the for loop
            if done:
                break
            current_state = next_state
        # We update the exploration proba using exponential decay formula
        exploration_proba = max(min_exploration_proba, np.exp(-exploration_decreasing_decay * e))
        total_rewards_episode.append(total_episode_reward)
