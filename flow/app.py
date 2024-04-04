from flask import Flask
from concurrent.futures import ThreadPoolExecutor

from collector.data_collector import DataCollector

app = Flask(__name__)

connection_created = False

@app.route('/create_connexion', methods=['POST'])
def hello_world():  # put application's code here
    global connection_created
    if not connection_created:
        connection_created = True
        data_collector = DataCollector()
        data_collector_threads = ThreadPoolExecutor(max_workers=10)
        data_collector_threads.submit(data_collector.get_data())
        return "Connection established"
    else:
        return "Connection already established"


if __name__ == '__main__':
    app.run()
