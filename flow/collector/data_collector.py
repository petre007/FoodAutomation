import base64
import threading
import requests
import json
from confluent_kafka import Producer

conf = {'bootstrap.servers': 'localhost:9093',
        'broker.address.family': 'v4',
        "default.topic.config": {"auto.offset.reset": "earliest"},
        'group.id': 'ULTRASONIC'}

get_data_url = "http://localhost:8080/robot/data"


class DataCollector:
    _instance = None
    _lock = threading.Lock()

    def __new__(cls):
        if cls._instance is None:
            with cls._lock:
                if not cls._instance:
                    cls._instance = super().__new__(cls)
        return cls._instance

    def __init__(self):
        self.producer = None
        self.ultrasonic_data = []
        self.esp32_data = []
        self.orders_data = []

    def _create_producer(self):
        if self.producer is None:
            self.producer = Producer(conf)
        return self.producer

    def get_data(self):
        response = requests.get(url=get_data_url, headers={"id": "1"})
        data = json.loads(response.content)
        self.ultrasonic_data = data["ultrasonic_data"]
        self.esp32_data = data["esp32_data"]

    def send_command(self, command):
        self.producer = self._create_producer()
        self.producer.produce("data_from_trained_model", value=command)
        self.producer.flush()
