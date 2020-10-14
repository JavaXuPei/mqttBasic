package com.mqtt.mqttbasis.controller;


import cn.hutool.core.lang.Validator;
import com.alibaba.fastjson.JSON;
import com.mqtt.mqttbasis.dto.MqttDto;
import com.mqtt.mqttbasis.service.MqttMessageService;
import com.mqtt.mqttbasis.utils.AjaxResult;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

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

    private final static String CONNECTION_TYPE = "tcp://";

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

    /**
     * 修改key的值
     */
    @RequestMapping(value = "/writeUpdate/Message", method = RequestMethod.POST)
    @ResponseBody
    public void mqttWrite(String k, String v) {
        k = JSON.toJSONString(k);
        v = JSON.toJSONString(v);
    }

    /**
     * 建立指定连接
     */
    @RequestMapping(value = "/custom/connection", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult createConnection(
            @RequestBody Map<String, Object> jsonObj) {
        for (String s : Arrays.asList("host", "port", "username", "password")) {
            if (Validator.isNull(jsonObj.get(s))) {
                return AjaxResult.fail("参数不能为空");
            }
        }
        String serviceUrl;
        try {
            serviceUrl = mqttMessageService.createConnection(CONNECTION_TYPE + jsonObj.get("host").toString(), jsonObj.get("port").toString(),
                    jsonObj.get("username").toString(),
                    jsonObj.get("password").toString());
        } catch (Exception e) {
            return AjaxResult.fail("连接失败");
        }
        return AjaxResult.success(serviceUrl, "建立连接成功");
    }

    /**
     * 查询已经建立的连接
     */

    @RequestMapping(value = "/custom/get/connection", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResult getConnection() {
        String serviceUrl = mqttMessageService.getConnection();
        return AjaxResult.success(serviceUrl, "ok");
    }

    /**
     * 自定义向topic发送消息
     */
    @RequestMapping(value = "/custom/pub", method = RequestMethod.POST)
    @ResponseBody
    public void createPub(String topic, String msg, int qos) throws MqttException {
        mqttMessageService.createPub(topic, msg, qos);
    }


    /**
     * 断开连接
     */
    @RequestMapping(value = "/custom/close", method = RequestMethod.POST)
    @ResponseBody
    public void createClose() throws MqttException {
        mqttMessageService.createClose();
    }


    /**
     * 自定义订阅topic的消息
     */
    @RequestMapping(value = "/custom/sub", method = RequestMethod.POST)
    @ResponseBody
    public void createSub(String topic) throws MqttException {
        mqttMessageService.createSub(topic);
    }

}
