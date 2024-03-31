import logging
from flask import Flask
from concurrent.futures import ThreadPoolExecutor
from connector.serial_comunication import SerialComunication

app = Flask(__name__)

connection_created = False

@app.route('/create_connexion', methods=['POST'])
def create_connexion():
    global connection_created
    if not connection_created:
        sercom = SerialComunication("/dev/ttyUSB0", 9600)
        print("Creating threads for serial comunication")
        logging.info("Creating threads for serial comunication")
        read_data_thread = ThreadPoolExecutor(max_workers=1)
        read_data_thread.submit(sercom.read_data())
        connection_created = True
        return "Connexion successful"
    else:
        return "Connexion already established"


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000, debug=True)
