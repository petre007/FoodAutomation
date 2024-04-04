package com.example.flux.kafka.utils;

public class KafkaUtils {

    // Group Id
    public static final String GROUP_ID_ULTRASONIC = "ULTRASONIC";
    public static final String GROUP_ID_ESP32 = "ESP32";

    // Topics where flux consumes data
    public static final String ULTRASONIC_DATA_CONSUMER = "data_from_ultrasonic";
    public static final String ESP32_DATA_CONSUMER = "data_from_esp32";

    // Topics where flux produces data
    public static final String ULTRASONIC_DATA_PRODUCER = "collected_data_from_ultrasonic";
    public static final String ESP32_DATA_PRODUCER = "collected_data_from_esp32";
}
