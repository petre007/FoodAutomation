from flask import Flask, jsonify
from confluent_kafka import Producer, Consumer

from connector.serial_comunication import SerialComunication

app = Flask(__name__)

# Kafka producer
producer = Producer({'bootstrap.servers': '<insert_external_ip>:9094', 'broker.address.family': 'v4'})
sercom = SerialComunication("/dev/ttyUSB0", 9600) # arduino


@app.route('/serial_comunication', methods=['POST'])
def serial_communication_test():
    try:
        while True:
            print("Data from arduino: " + sercom.read_data())
            producer.produce('data_from_arduino', value=sercom.read_data())
            producer.flush()
    except Exception as e:
        return jsonify({'error': str(e)}), 500


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000, debug=True)
