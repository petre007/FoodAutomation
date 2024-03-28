from flask import Flask, jsonify
from confluent_kafka import Producer, Consumer, KafkaError

from connector.serial_comunication import SerialComunication

app = Flask(__name__)

conf = {'bootstrap.servers': '192.168.1.128:9094',
        'broker.address.family': 'v4',
        "default.topic.config": {"auto.offset.reset": "earliest"},
        'group.id': 'ULTRASONIC'}

# Kafka producer
producer = Producer(conf)
consumer = Consumer(conf)
sercom = SerialComunication("/dev/ttyUSB0", 9600)


@app.route('/serial_comunication', methods=['POST'])
def serial_communication_test():
    try:
        while True:
            if "ULTRASONIC" in sercom.read_data():
                print("Data from arduino: " + sercom.read_data())
                producer.produce('data_from_ultrasonic',
                                 value=sercom.read_data().replace("ULTRASONIC", ""))
                producer.flush()
    except Exception as e:
        return jsonify({'error': str(e)}), 500


# this part will be integrated in another microservice
consumer.subscribe(['collected_data_from_ultrasonic'])


@app.route('/consumer_test', methods=['POST'])
def consumer_test():
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
                value = msg.value().decode('utf-8')
                print("Consumer value: " + value)

    except KeyboardInterrupt:
        pass
    finally:
        # Close the consumer gracefully
        consumer.close()


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000, debug=True)
