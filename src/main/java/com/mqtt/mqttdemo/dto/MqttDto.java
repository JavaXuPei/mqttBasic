package com.mqtt.mqttdemo.dto;

import java.util.Collection;

public class MqttDto {

    private Collection<MessageDto> values;

    public Collection<MessageDto> getValues() {
        return values;
    }

    public Collection<MessageDto> setValues(Collection<MessageDto> values) {
     return  this.values = values;
    }
}
