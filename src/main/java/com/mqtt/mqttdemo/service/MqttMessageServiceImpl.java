package com.mqtt.mqttdemo.service;

import com.mqtt.mqttdemo.controller.MqttInboundConfig;
import com.mqtt.mqttdemo.controller.MqttOutboundConfig;
import com.mqtt.mqttdemo.official.LimitQueue;
import com.mqtt.mqttdemo.official.SpringUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class MqttMessageServiceImpl implements MqttMessageService {


    /**
     * 读
     *
     * @return
     */
    @Override
    public String mqttInbounUpDate() {
        LimitQueue<String> lqueue = MqttInboundConfig.lqueue;
        return lqueue.peek();
    }

    /**
     * 写
     * @return
     */
    @Override
    public void mqttOutbound(String message) {
        ApplicationContext ctx = SpringUtil.getApplicationContext();
        MqttOutboundConfig.MyGateway gateway = ctx.getBean(MqttOutboundConfig.MyGateway.class);
        gateway.sendToMqtt(message);
    }
}
