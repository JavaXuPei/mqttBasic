package com.mqtt.mqttbasis.controller;


import com.alibaba.fastjson.JSON;
import com.mqtt.mqttbasis.dto.MqttDto;
import com.mqtt.mqttbasis.service.MqttMessageService;
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


    /**
     * 获取最新的mqtt消息
     *
     * @return
     */
    @RequestMapping(value = "/newest", method = RequestMethod.GET)
    @ResponseBody
    public MqttDto mqttInbounUpDate() {
        return mqttMessageService.mqttInbounUpDate();
    }


    /**
     * 获取指定key的最新消息
     *
     * @param key
     * @return
     */
    @RequestMapping(value = "/newest/{key}", method = RequestMethod.GET)
    @ResponseBody
    public MqttDto mqttInbounUpDateByKey(@PathVariable(value = "key") String key) {
        return mqttMessageService.mqttInbounUpDateByKey(key);
    }

    /**
     * 获取指定的多个key的最新消息
     *
     * @param keys
     * @return
     */
    @RequestMapping(value = "/newest/multiple/{keys}", method = RequestMethod.GET)
    @ResponseBody
    public MqttDto mqttInbounUpDateByKeyArray(@PathVariable(value = "keys") String keys) {
        String[] keyArray = keys.split(",");
        return mqttMessageService.mqttInbounUpDateByKeyArray(keyArray);
    }

    /**
     * 写入消息
     */
    @RequestMapping(value = "/write/Message", method = RequestMethod.POST)
    @ResponseBody
    public void mqttWrite(String message) {
        String ms = JSON.toJSONString(message);
        mqttMessageService.mqttOutbound(ms);
    }
}
