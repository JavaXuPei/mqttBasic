package com.mqtt.mqttbasis.mqtt.modbus.dto;

import lombok.Data;

@Data
public class MqttBaseDto {

    private Long ts;
    private String name;
    private String value;
}
