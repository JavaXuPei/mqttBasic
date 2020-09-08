package com.mqtt.mqttdemo;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAdminServer
@SpringBootApplication
public class MqttdemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MqttdemoApplication.class, args);
    }

}
