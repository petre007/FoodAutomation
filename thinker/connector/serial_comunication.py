import threading
import time

import serial
from confluent_kafka import Producer, Consumer, KafkaError

conf = {'bootstrap.servers': '192.168.1.128:9094',
        'broker.address.family': 'v4',
        "default.topic.config": {"auto.offset.reset": "earliest"},
        'group.id': 'OUTPUT_2'}

topics_list = ['robot_commands']

command_event1 = threading.Event()
command_event2 = threading.Event()

def read_data_from_arduino():
    producer = Producer(conf)
    print("started read_data_from_arduino")
    ser = serial.Serial(port="/dev/ttyUSB0", baudrate=9600)
    while True:
        command_event1.wait()
        command_event1.clear()
        print("Producing ultrasonic value")
        line = ser.readline().decode('utf-8').rstrip()
        time.sleep(0.5)
        print(line)
        if "ULTRASONIC" in line:
            producer.produce('data_from_ultrasonic',
                             value=line.replace("ULTRASONIC", ""))
            producer.flush()
            print("Ultrasonic value produced")


def read_data_from_esp32():
    producer = Producer(conf)
    print("Started read_data_from_esp32")
    ser = serial.Serial(port="/dev/ttyUSB1", baudrate=115200)
    while True:
        command_event2.wait()
        command_event2.clear()
        print("Producing image")
        ser.write(bytes("START", 'utf-8'))
        image_data = ser.readline().decode('utf-8').rstrip()
        time.sleep(0.5)
        producer.produce('data_from_esp32',
                         value=image_data)
        producer.flush()
        print("Image produced")


def send_data():
    consumer = Consumer(conf)
    ser = serial.Serial(port="/dev/ttyUSB0", baudrate=9600)
    print("Started send_data")
    consumer.subscribe(topics_list)
    try:
        while True:
            msg = consumer.poll(1.0)
            if msg is None:
                continue
            if msg.error():
                if msg.error().code() == KafkaError._PARTITION_EOF:
                    continue
                else:
                    print(f'Error while consuming: {msg.error()}')
            else:
                # Parse the received message
                val = msg.value().decode('utf-8')
                topic = msg.topic()
                if topic == 'robot_commands':
                    command_event1.set()
                    command_event2.set()
                    ser.write(bytes(val, 'utf-8'))
                    ser.flush()
                    print("Here")
                print(f'Received: {val} from topic {topic}    ')
                consumer.commit(msg)
                time.sleep(2)

    except KeyboardInterrupt:
        pass
    finally:
        consumer.close()
        ser.close()
