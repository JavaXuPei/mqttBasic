package com.mqtt.mqttbasis.mqtt.modbus.dto;

import lombok.Data;

import java.util.Map;

/**
 * @author bjy
 */
@Data
public class MqttDto {
    private Long ts;

    private Map<String, Object> values;

    public Long getTs() {
        return ts;
    }

    public void setTs(Long ts) {
        this.ts = ts;
    }

    public Map<String, Object> getValues() {
        return values;
    }

    public void setValues(Map<String, Object> values) {
        this.values = values;
    }
}
