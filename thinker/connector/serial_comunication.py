import serial
from confluent_kafka import Producer, Consumer, KafkaError

conf = {'bootstrap.servers': '51.107.12.112:80',
        'broker.address.family': 'v4',
        "default.topic.config": {"auto.offset.reset": "earliest"},
        'group.id': 'OUTPUT_2'}

topics_list = ['robot_commands']


def read_data_from_arduino():
    producer = Producer(conf)
    print("started read_data_from_arduino")
    ser = serial.Serial(port="/dev/ttyUSB0", baudrate=9600)
    while True:
        line = ser.readline().decode('utf-8').rstrip()
        if "ULTRASONIC" in line:
            producer.produce('data_from_ultrasonic',
                             value=line.replace("ULTRASONIC", ""))
            producer.flush()


def read_data_from_esp32():
    producer = Producer(conf)
    print("Started read_data_from_esp32")
    ser = serial.Serial(port="/dev/ttyUSB1", baudrate=115200)
    while True:
        image_data = ser.readline().decode('utf-8').rstrip()
        producer.produce('data_from_esp32',
                         value=image_data)
        producer.flush()


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
                print(val)
                topic = msg.topic()
                if topic == 'robot_commands':
                    ser.write(bytes(val, 'utf-8'))
                    ser.flush()
                print(f'Received: {val} from topic {topic}    ')
                consumer.commit(msg)

    except KeyboardInterrupt:
        pass
