from flask import Flask

from collector.data_collector import DataCollector
from brain.rl_model import rl_model
from flask_cors import CORS, cross_origin

app = Flask(__name__)
cors = CORS(app)
app.config['CORS_HEADERS'] = 'Content-Type'

connection_created = False
data_collector = DataCollector()


@app.route('/create_connexion', methods=['POST'])
@cross_origin()
def create_connexion():  # put application's code here
    global connection_created, data_collector
    print("create_connexion method")
    if not connection_created:
        connection_created = True
        return "Connection established"
    else:
        return "Connection already established"


@app.route('/rl_model', methods=['GET'])
@cross_origin()
def start_rl_model():
    print("start_rl_model called")
    rl_model()


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000)
