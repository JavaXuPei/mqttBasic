package com.mqtt.mqttdemo.controller;


import com.mqtt.mqttdemo.dto.MqttDto;
import com.mqtt.mqttdemo.service.MqttMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 控制层
 *
 * @author bjy
 */
@RequestMapping("/api")
@RestController
public class MqttController {


    @Autowired
    private MqttMessageService mqttMessageService;

    @RequestMapping(value = "/newest", method = RequestMethod.GET)
    @ResponseBody
    public MqttDto mqttInbounUpDate() {
        return mqttMessageService.mqttInbounUpDate();
    }

    @RequestMapping(value = "/newest/{key}", method = RequestMethod.GET)
    @ResponseBody
    public MqttDto mqttInbounUpDateByKey(@PathVariable(value = "key") String key) {
        return mqttMessageService.mqttInbounUpDateByKey(key);
    }

    @RequestMapping(value = "/newest/multiple/{keys}", method = RequestMethod.GET)
    @ResponseBody
    public MqttDto mqttInbounUpDateByKeyArray(@PathVariable(value = "keys")  String keys) {
        String[] keyArray = keys.split(",");
        return mqttMessageService.mqttInbounUpDateByKeyArray(keyArray);
    }
}
