package com.mqtt.mqttdemo.service;

public interface MqttMessageService {


    /**
     * 读
     */
    String mqttInbounUpDate();

    /**
     * 写
     */
    void mqttOutbound(String message);
}
