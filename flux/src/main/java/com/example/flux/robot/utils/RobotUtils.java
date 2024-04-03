package com.example.flux.robot.utils;

public class RobotUtils {

    public final static long MAX_LIMIT = 1000;

    public final static String CONNEXION_SUCCESSFUL = "successful";
    public final static String CONNEXION_NOT_SUCCESSFUL = "not_successful";
    public final static String CREATE_CONNEXION_ENDPOINT = "create_connexion";

    public static String getURL(String connexionString, String port, String endpoint) {
        if (port == null) {
            port = "";
        }
        if (connexionString == null) {
            connexionString = "";
        }
        if (endpoint == null) {
            endpoint = "";
        }
        return "http://connexion:port/endpoint".replace("connexion", connexionString)
                .replace("port", port)
                .replace("endpoint", endpoint);
    }

}
