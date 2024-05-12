from flask import Flask

from collector.data_collector import DataCollector

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


if __name__ == '__main__':
    app.run(debug=True)
