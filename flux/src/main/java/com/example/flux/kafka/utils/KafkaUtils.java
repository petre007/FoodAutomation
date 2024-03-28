package com.example.flux.kafka.utils;

public class KafkaUtils {

    // Topics where flux consumes data
    public static final String ULTRASONIC_DATA_CONSUMER = "data_from_ultrasonic";

    // Topics where flux produces data
    public static final String ULTRASONIC_DATA_PRODUCER = "collected_data_from_ultrasonic";
}
