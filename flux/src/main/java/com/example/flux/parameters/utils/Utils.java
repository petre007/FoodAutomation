package com.example.flux.parameters.utils;

public class Utils {

    public static boolean validateDoubleValue(String value) {
        try {
            Double.parseDouble(value);
        }catch (Exception e) {
            throw new IllegalArgumentException("Could not convert from string to double the value "+value);
        }
        return true;
    }


    public static boolean validateIntegerValue(String value) {
        try {
            Integer.parseInt(value);
        }catch (Exception e) {
            throw new IllegalArgumentException("Could not convert from string to double the value "+value);
        }
        return true;
    }
}
