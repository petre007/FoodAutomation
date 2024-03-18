from flask import Flask, jsonify
from confluent_kafka import Producer, Consumer

from connector.serial_comunication import SerialComunication

app = Flask(__name__)

# Kafka producer
producer = Producer({'bootstrap.servers': '<insert_ip>:9093', 'broker.address.family': 'v4'})
sercom = SerialComunication("/dev/ttyUSB0", 9600)


@app.route('/serial_comunication', methods=['POST'])
def serial_communication_test():
    try:
        producer.produce('data_from_arduino', value=sercom.read_data())
        producer.flush()
        return "Message was sent to kafka"
    except Exception as e:
        return jsonify({'error': str(e)}), 500


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000, debug=True)
