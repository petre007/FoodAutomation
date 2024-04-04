from confluent_kafka import Consumer, KafkaError

conf = {'bootstrap.servers': 'localhost:9093',
        'broker.address.family': 'v4',
        "default.topic.config": {"auto.offset.reset": "earliest"},
        'group.id': 'ULTRASONIC'}


class DataCollector:

    def __new__(cls):
        if not hasattr(cls, 'instance'):
            cls.instance = super(DataCollector, cls).__new__(cls)
        return cls.instance

    def __init__(self):
        self.consumer = None
        self.ultrasonic_data = []
        self.esp32_data = []

    def _create_consumer(self):
        if self.consumer is None:
            self.consumer = Consumer(conf)
        return self.consumer

    def get_data(self):
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
                        self.ultrasonic_data = val
                    if topic is 'collected_data_from_esp32':
                        self.esp32_data = val
                    print(f'Received: {val} from topic {topic}    ')
                    self.consumer.commit(msg)

        except KeyboardInterrupt:
            pass
        finally:
            # Close the consumer gracefully
            self.consumer.close()
