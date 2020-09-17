package com.mqtt.mqttdemo;


import com.mqtt.mqttbasis.MqttBasisApplication;
import com.mqtt.mqttbasis.service.MqttMessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MqttBasisApplication.class)
public class test {

    @Autowired
    private MqttMessageService mqttMessageService;

    @Test
    public void mqttOutbound() {
        String content = "我是一条mqtt消息内容";
        mqttMessageService.mqttOutbound(content);
    }

}
