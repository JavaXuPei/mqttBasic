package com.mqtt.mqttbasis.mqtt.modbus.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;

class ModbusValueTest {
    @Test
    void contextLoads() {
        String str = ResourceUtil.readUtf8Str("demo.json");
        System.out.println(DateUtil.now());
        String obj = JSON.toJSONString(JSON.parse(str));
        System.out.println(JSON.toJSONString(ModbusValue.getModbusValue(obj, "CGQ-001")));
        System.out.println(DateUtil.now());
    }
}