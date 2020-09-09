package com.mqtt.mqttdemo.service;

import com.mqtt.mqttdemo.dto.MqttDto;

public interface MqttMessageService {


    /**
     * 读
     * @return
     */
    MqttDto mqttInbounUpDate();

    /**
     * key
     */
    MqttDto mqttInbounUpDateByKey(String key);

    /**
     * keyArray
     */
    MqttDto mqttInbounUpDateByKeyArray(String[] key);

    /**
     * 写
     */
    void mqttOutbound(String message);
}
