import os
import time

from brain.environment import RobotEnv
from confluent_kafka import Producer

os.environ["KERAS_BACKEND"] = "tensorflow"

import numpy as np

conf = {'bootstrap.servers': 'localhost:9093',
        'broker.address.family': 'v4',
        "default.topic.config": {"auto.offset.reset": "earliest"},
        'group.id': 'FLOW-GROUPID'}


def rl_model_train():
    # Instantiate environment and agent
    env = RobotEnv()
    env.is_training = True

    try:
        Q_table = env.load()
    except:
        state_size = env.observation_space.n
        action_size = env.action_space.n
        Q_table = np.zeros((state_size, action_size))

    actions = env.data_collector.output_data

    if len(actions) == 0:
        print("Not enough data to begin training the model")
        return

    # number of episode we will run
    n_episodes = 1

    # maximum of iteration per episode
    max_iter_episode = len(actions)

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
            # if the sampled float is less than the exploration proba
            #     the agent selects a random action
            # else
            #     he exploits his knowledge using the bellman equation

            # if np.random.uniform(0, 1) < exploration_proba:
            #     print("action here")
            #     action = env.action_space.sample()
            # else:
            #     print("action there")
            #     action = np.argmax(Q_table[current_state, :])

            action = actions[i]

            # The environment runs the chosen action and returns
            # the next state, a reward and true if the episod is ended.
            next_state, reward, done = env.step(action)
            print(next_state)
            print(reward)
            print(done)
            # We update our Q-table using the Q-learning iteration
            Q_table[current_state, action] = (1 - lr) * Q_table[current_state, action] + lr * (
                    reward + gamma * np.max(Q_table[next_state, :]))
            total_episode_reward = total_episode_reward + reward
            # If the episode is finished, we leave the for loop
            if done:
                print("The q-table model was created and saved...")
                env.save(Q_table)
                break
            current_state = next_state
        # We update the exploration proba using exponential decay formula
        exploration_proba = max(min_exploration_proba, np.exp(-exploration_decreasing_decay * e))
        total_rewards_episode.append(total_episode_reward)


# use this method when an order is on the Queue
# use something like done to exit the scope
# MAYBE SAVE ALL THE ACTIONS AND MAKE THE ROBOT COME BACK TO THE CURRENT POSITION
def rl_model():
    producer = Producer(conf)
    env = RobotEnv()
    env.is_training = False
    current_state = env.reset()

    Q_table = env.load()
    while not env.is_training:
        print("Robot state: " + str(current_state))
        action = int(np.argmax(Q_table[current_state, :]))
        next_state, done = env.step(action)
        current_state = next_state
        print("Robot action: " + str(action))
        if done:
            print("Order delivered")
            break
        producer.produce("output_from_rl_model", value=str(action))
        producer.flush()
        time.sleep(1)
