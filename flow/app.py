from flask import Flask

from collector.data_collector import DataCollector
from brain.rl_model import rl_model

app = Flask(__name__)

connection_created = False
data_collector = DataCollector()


@app.route('/create_connexion', methods=['POST'])
def create_connexion():  # put application's code here
    global connection_created, data_collector
    if not connection_created:
        connection_created = True
        data_collector.get_data()
        return "Connection established"
    else:
        return "Connection already established"


@app.route('/rl_model', methods=['GET'])
def start_rl_model():
    print("start_rl_model called")
    rl_model()


if __name__ == '__main__':
    app.run(debug=True)
