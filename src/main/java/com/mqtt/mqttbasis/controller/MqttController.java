package com.mqtt.mqttbasis.controller;


import cn.hutool.core.lang.Validator;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mqtt.mqttbasis.dto.MqttDto;
import com.mqtt.mqttbasis.entity.ConnectionConfigEntity;
import com.mqtt.mqttbasis.mapper.ConnectionConfigMapper;
import com.mqtt.mqttbasis.mqtt.MqttOutboundConfig;
import com.mqtt.mqttbasis.service.MqttMessageService;
import com.mqtt.mqttbasis.utils.AjaxResult;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

/**
 * @author bjy
 */

@RequestMapping("/api")
@RestController
public class MqttController {

    @Autowired
    private ConnectionConfigMapper connectionConfigMapper;

    @Autowired
    private MqttMessageService mqttMessageService;

    private final static String CONNECTION_TYPE = "tcp://";
    private final static String HOST = "host";
    private final static String PORT = "port";
    private final static String USERNAME = "username";
    private final static String PASSWORD = "password";
    private final static String TOPIC = "topic";

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

    @RequestMapping(value = "/del/test", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public AjaxResult delTest() {
        return AjaxResult.fail("错误");
    }

    /**
     * 建立指定连接
     */
    @RequestMapping(value = "/custom/connection", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResult createConnection(
            @RequestBody Map<String, Object> jsonObj) {
        for (String s : Arrays.asList(HOST, PORT, USERNAME, PASSWORD, TOPIC)) {
            if (Validator.isNull(jsonObj.get(s))) {
                return AjaxResult.fail("参数不能为空");
            }
        }
        String serviceUrl;
        try {
            serviceUrl = mqttMessageService.createConnection(CONNECTION_TYPE + jsonObj.get(HOST).toString(), jsonObj.get(PORT).toString(),
                    jsonObj.get("username").toString(),
                    jsonObj.get("password").toString(),
                    jsonObj.get("topic").toString()
            );
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
     * 查询已经建立的连接
     */
    @RequestMapping(value = "/custom/get/Dbconnection", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResult getDbConnection() {
        ConnectionConfigEntity connectionConfigEntity = connectionConfigMapper.selectOne(new QueryWrapper<ConnectionConfigEntity>().eq("is_delete", 0));
        return AjaxResult.success(connectionConfigEntity, "ok");
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
    @RequestMapping(value = "/custom/close", method = RequestMethod.GET)
    @ResponseBody
    public AjaxResult createClose() {
        try {
            mqttMessageService.createClose();
        } catch (Exception e) {
            return AjaxResult.fail("断开连接失败");
        }
        return AjaxResult.success("断开连接成功");
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
