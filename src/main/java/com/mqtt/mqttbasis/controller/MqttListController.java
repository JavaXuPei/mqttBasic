package com.mqtt.mqttbasis.controller;

import com.alibaba.fastjson.JSONObject;
import com.mqtt.mqttbasis.official.LimitQueue;
import com.mqtt.mqttbasis.utils.AjaxResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequestMapping("/api")
@RestController
public class MqttListController {

    /**
     * 获取mqttjson消息中所有的键值
     */
    @RequestMapping(value = "/get/mqttKeyAll", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResult getMqttKeyAll() {
        LimitQueue<String> lqueue = MqttInboundConfig.lqueue;
        String mqttMessage = lqueue.peek();
        Map<String, Object> map = JSONObject.parseObject(mqttMessage);
        return AjaxResult.success(map.keySet(), "ok");
    }
}
