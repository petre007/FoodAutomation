from flask import Flask
from concurrent.futures import ThreadPoolExecutor
from connector.serial_comunication import send_data, read_data_from_arduino, read_data_from_esp32

app = Flask(__name__)

connection_created = False


@app.route('/create_connexion', methods=['POST'])
def create_connexion():
    global connection_created
    if not connection_created:
        connection_created = True
        print("Creating threads for serial comunication")
        threads = ThreadPoolExecutor(max_workers=3)
        threads.submit(send_data)
        threads.submit(read_data_from_arduino)
        threads.submit(read_data_from_esp32)
        return "Connexion successful"

    else:
        return "Connexion already established"


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000, debug=True)
