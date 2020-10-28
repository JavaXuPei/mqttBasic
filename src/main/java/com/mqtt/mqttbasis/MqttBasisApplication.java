package com.mqtt.mqttbasis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

/**
 * @author bjy
 */
@Configuration
@MapperScan("com.mqtt.mqttbasis.mapper")
@SpringBootApplication
public class MqttBasisApplication {
    public static void main(String[] args) {
        SpringApplication.run(MqttBasisApplication.class, args);
    }

}
