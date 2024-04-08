import threading

from confluent_kafka import Consumer, KafkaError

conf = {'bootstrap.servers': 'localhost:9093',
        'broker.address.family': 'v4',
        "default.topic.config": {"auto.offset.reset": "earliest"},
        'group.id': 'ULTRASONIC'}


class DataCollector:
    _instance = None

    def __new__(class_, *args, **kwargs):
        if not isinstance(class_._instance, class_):
            class_._instance = object.__new__(class_)
        return class_._instance

    def __init__(self):
        self.consumer = None
        self.ultrasonic_data = []
        self.esp32_data = []

    def _create_consumer(self):
        if self.consumer is None:
            self.consumer = Consumer(conf)
        return self.consumer

    def _get_data(self):
        self.consumer = self._create_consumer()
        self.consumer.subscribe(['collected_data_from_ultrasonic', 'collected_data_from_esp32'])
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
                    if topic is 'collected_data_from_ultrasonic':
                        self.ultrasonic_data = list(val)
                    if topic is 'collected_data_from_esp32':
                        self.esp32_data = list(val)
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
