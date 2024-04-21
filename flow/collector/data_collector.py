import threading

from confluent_kafka import Producer, Consumer, KafkaError

conf = {'bootstrap.servers': 'localhost:9093',
        'broker.address.family': 'v4',
        "default.topic.config": {"auto.offset.reset": "earliest"},
        'group.id': 'ULTRASONIC'}

topics_list = ['collected_data_from_ultrasonic', 'collected_data_from_esp32', 'orders_delivering']


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
        self.consumer = None
        self.producer = None
        self.ultrasonic_data = []
        self.esp32_data = []
        self.orders_data = []

    def _create_consumer(self):
        if self.consumer is None:
            self.consumer = Consumer(conf)
        return self.consumer

    def _create_producer(self):
        if self.producer is None:
            self.producer = Producer(conf)
        return self.producer

    def _get_data(self):
        self.consumer = self._create_consumer()
        self.consumer.subscribe(topics_list)
        try:
            while True:
                msg = self.consumer.poll(1.0)

                if msg is None:
                    continue
                if msg.error():
                    if msg.error().code() == KafkaError._PARTITION_EOF:
                        continue
                    else:
                        print(f'Error while consuming: {msg.error()}')
                else:
                    # Parse the received message
                    val = msg.value().decode('utf-8')
                    topic = msg.topic()
                    if topic == 'collected_data_from_ultrasonic':
                        self.ultrasonic_data = list(val)
                    if topic == 'collected_data_from_esp32':
                        self.esp32_data = list(val)
                    if topic == 'orders_delivering':
                        self.orders_data.append(val)
                    print(f'Received: {val} from topic {topic}    ')
                    self.consumer.commit(msg)

        except KeyboardInterrupt:
            pass
        finally:
            # Close the consumer gracefully
            self.consumer.close()

    def start_data_collection(self):
        print("Starting thread for data collecting")
        data_collection_thread = threading.Thread(target=self._get_data)
        data_collection_thread.daemon = True
        data_collection_thread.start()

    def send_command(self, command):
        self.producer = self._create_producer()
        self.producer.produce("data_from_trained_model", value=command)
        self.producer.flush()
