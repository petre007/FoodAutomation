import serial
import sys
from confluent_kafka import Producer, Consumer, KafkaError
from flask import jsonify

conf = {'bootstrap.servers': '192.168.1.128:9094',
        'broker.address.family': 'v4',
        "default.topic.config": {"auto.offset.reset": "earliest"},
        'group.id': 'ULTRASONIC'}

# Kafka producer
producer = Producer(conf)
consumer = Consumer(conf)

class SerialComunication:
    def __init__(self, port, baudrate):
        self.port = port
        self.baudrate = baudrate
        self.ser = None

    def _configure_serial_communication(self):
        if self.ser is None:
            self.ser = serial.Serial(port=self.port, baudrate=self.baudrate, timeout=1)
        return self.ser

    def read_data(self):
        ser = self._configure_serial_communication()
        try:
            while True:
                line = ser.readline().decode('utf-8').rstrip()
                print("Data: " + line)
                if "ULTRASONIC" in line:
                    print("Data from arduino: " + line)
                    producer.produce('data_from_ultrasonic',
                                     value=line.replace("ULTRASONIC", ""))
                    producer.flush()
        except Exception as e:
            return jsonify({'error': str(e)}), 500


    def send_data(self, data):
        ser = self._configure_serial_communication()
        ser.write(data)
        ser.flush()


    # consumer.subscribe(['collected_data_from_ultrasonic'])

    # @app.route('/create', methods=['POST'])
    # def consumer_test():
    #     try:
    #         while True:
    #             msg = consumer.poll(1.0)
    #
    #             if msg is None:
    #                 continue
    #             if msg.error():
    #                 if msg.error().code() == KafkaError._PARTITION_EOF:
    #                     continue
    #                 else:
    #                     print(f'Error while consuming: {msg.error()}')
    #             else:
    #                 # Parse the received message
    #                 value = msg.value().decode('utf-8')
    #                 print("Consumer value: " + value)
    #
    #     except KeyboardInterrupt:
    #         pass
    #     finally:
    #         # Close the consumer gracefully
    #         consumer.close()