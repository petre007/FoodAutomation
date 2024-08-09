# Automated Food Delivery System for Hotels

## Project Overview

This project focuses on creating an automated food delivery system for hotels, using a combination of hardware and software solutions. The system is designed to autonomously deliver food from the hotel kitchen to guest rooms, improving service efficiency and reducing the need for manual labor.

## System Architecture

### Hardware Components

- **Autonomous Robot**: Equipped with sensors and a microcontroller for navigation and obstacle avoidance.
- **Arduino & Raspberry Pi**: Central to controlling the robot, these devices manage sensor data, motor control, and communication with the server.

### Software Components

- **Microservices Architecture**: Built using Spring Boot and Flask to ensure modularity and scalability.
- **Angular Frontend**: Provides an intuitive user interface for hotel staff and system administrators.

### Cloud Infrastructure

- **Azure Cloud Services**: Used for deploying the system’s backend, ensuring it is scalable and reliable.

## Workflow

1. **Order Placement**: Hotel staff or guests place a food order through the system interface.
2. **Order Processing**: The server processes the order and assigns a delivery task to the autonomous robot.
3. **Navigation and Delivery**: The robot, guided by its sensors and AI models, navigates the hotel to deliver the order to the guest's room.
4. **Completion**: After delivery, the robot returns to the kitchen or charging station, ready for the next task.

## Key Features

- **Autonomous Navigation**: The robot uses AI-based algorithms to move autonomously within the hotel environment.
- **Real-Time Communication**: The system employs a message broker for efficient and reliable communication between components.
- **User Interface**: Developed with Angular, the UI allows easy management of orders and monitoring of the robot’s status.

## Conclusion

This project demonstrates a functional prototype for automating food delivery in hotels, highlighting the benefits of integrating robotics, cloud computing, and microservices.
