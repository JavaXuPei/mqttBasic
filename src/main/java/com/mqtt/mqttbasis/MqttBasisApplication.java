package com.mqtt.mqttbasis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@Configuration
@SpringBootApplication
public class MqttBasisApplication {
    public static void main(String[] args) {
        SpringApplication.run(MqttBasisApplication.class, args);
    }

}
