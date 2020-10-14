package com.mqtt.mqttbasis.service;

import com.mqtt.mqttbasis.dto.MqttDto;
import org.eclipse.paho.client.mqttv3.MqttException;

public interface MqttMessageService {


    /**
     * 读
     *
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

    /**
     * 自定义建立连接
     */
    String createConnection(String host, String port, String username, String password) throws MqttException;

    /**
     * 查询建立的连接
     */
    String getConnection();
    /**
     * 自定义向topic发送消息
     */
    void createPub(String topic, String msg, int qos) throws MqttException;

    /**
     * 自定义订阅topic
     */
    void createSub(String topic) throws MqttException;

    /**
     * 关闭连接
     */
    void createClose() throws MqttException;
}
