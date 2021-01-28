package com.mqtt.mqttbasis.mqtt.modbus.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mqtt.mqttbasis.mqtt.modbus.dto.MqttBaseDto;
import com.mqtt.mqttbasis.mqtt.modbus.dto.MqttDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author yaya
 */
public class ModbusValue {

    /**
     * @param json  demo.json内容
     * @param getKey demo.json中的key=“CGQ-001”
     * @return
     */
    public static List<MqttBaseDto> getModbusValue(String json, String getKey) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        String toJson = JSON.toJSONString(((Map<String, Object>) jsonObject).get(getKey));
        List<MqttDto> mqttDtoList = JSONObject.parseArray(toJson, MqttDto.class);
        List<MqttBaseDto> mqttBaseDtos = new ArrayList<>();
        mqttDtoList.forEach(mqttDto -> {
            MqttBaseDto mqttBaseDto = new MqttBaseDto();
            mqttBaseDto.setTs(mqttDto.getTs());
            mqttDto.getValues().keySet().forEach(mqttBaseDto::setName);
            mqttBaseDto.setValue(mqttDto.getValues().get(mqttBaseDto.getName()).toString());
            mqttBaseDtos.add(mqttBaseDto);
        });
        return mqttBaseDtos;
    }
}
