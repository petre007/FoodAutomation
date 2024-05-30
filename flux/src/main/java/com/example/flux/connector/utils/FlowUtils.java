package com.example.flux.connector.utils;

import java.util.HashMap;
import java.util.Map;

public class FlowUtils {

    // Flow utils: endpoints + name
    public final static String FLOW = "flow";
    public final static String FLOW_RL_MODEL= "rl_model";

    public static String getFlowEndpoints(String key) {
        Map<String, String> flowEndPoints = new HashMap<>();
        flowEndPoints.put(FLOW_RL_MODEL, "/rl_model");
        return flowEndPoints.get(key);
    }



}
