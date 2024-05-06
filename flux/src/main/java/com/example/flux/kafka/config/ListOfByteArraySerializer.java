package com.example.flux.kafka.config;

import org.apache.kafka.common.serialization.Serializer;

import java.util.Base64;
import java.util.List;
import java.util.Map;

public class ListOfByteArraySerializer implements Serializer<List<byte[]>> {
    @Override
    public byte[] serialize(String topic, List<byte[]> data) {
        StringBuilder sb = new StringBuilder();
        for (byte[] byteArray : data) {
            // Encode each byte array using Base64 and append it to the StringBuilder
            sb.append(Base64.getEncoder().encodeToString(byteArray));
            sb.append(";"); // Delimiter
        }
        return sb.toString().getBytes();
    }

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public void close() {
    }
}
