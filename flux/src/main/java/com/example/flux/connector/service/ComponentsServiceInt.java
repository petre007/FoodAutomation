package com.example.flux.connector.service;

import java.util.Map;

public interface ComponentsServiceInt {

    void callEndpoint(String endpoint, Map<?,?> requestBody, HttpMethodsEnum httpMethod) throws UnsupportedOperationException;

}
