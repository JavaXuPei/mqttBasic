package com.mqtt.mqttbasis.controller;

import com.alibaba.fastjson.JSON;
import com.mqtt.mqttbasis.official.LimitQueue;
import com.mqtt.mqttbasis.utils.AjaxResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

@RequestMapping("/api")
@RestController
public class MqttListController {
    static HashSet<String> stringList=new HashSet<>();
}
