package com.mqtt.mqttdemo.controller;

import org.springframework.beans.factory.annotation.Value;

/**
 * mqtt配置类
 */
public class MqttConfig {

    @Value("${mqttconfig.queueSize}")
    private static Integer queueSize;

    @Value("${mqttconfig.serverUrls}")
    private static String serverUrls;

    @Value("${mqttconfig.userName}")
    private static String userName;

    @Value("${mqttconfig.password}")
    private static String password;

    @Value("${mqttconfig.topic}")
    private static String topic;

    @Value("${mqttconfig.completionTimeout}")
    private static Integer completionTimeout;

    @Value("${mqttconfig.qos}")
    private static Integer qos;

    @Value("${mqttconfig.clientId}")
    private static String clientId;

    public MqttConfig() {
    }
    public static String getClientId() {
        return clientId;
    }

    public static Integer getQueueSize() {
        return queueSize;
    }

    public static String[] getServerUrls() {
        return serverUrls.split(",");
    }

    public static String getUserName() {
        return userName;
    }

    public static String getPassword() {
        return password;
    }

    public static String getTopic() {
        return topic;
    }

    public static Integer getCompletionTimeout() {
        return completionTimeout;
    }

    public static Integer getQos() {
        return qos;
    }
}
