package com.mqtt.mqttbasis.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mqtt.mqttbasis.controller.MqttInboundConfig;
import com.mqtt.mqttbasis.controller.MqttOutboundConfig;
import com.mqtt.mqttbasis.controller.MqttCallbackImpl;
import com.mqtt.mqttbasis.dto.MessageDto;
import com.mqtt.mqttbasis.dto.MqttDto;
import com.mqtt.mqttbasis.entity.ConnectionConfigEntity;
import com.mqtt.mqttbasis.official.LimitQueue;
import com.mqtt.mqttbasis.official.SpringUtil;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MqttMessageServiceImpl implements MqttMessageService {
    private MqttClient mqttClient;
    private final String clientId = "bjy" + System.currentTimeMillis();

    /**
     * 读
     *
     * @return 最新的mqtt数据
     */
    @Override
    public MqttDto mqttInbounUpDate() {
        LimitQueue<String> lqueue = MqttInboundConfig.lqueue;
        MqttDto mqttDto = null;
        if (lqueue.peek() != null) {
            mqttDto = JSONObject.parseObject(lqueue.peek(), MqttDto.class);
        }
        return mqttDto;
    }

    @Override
    public MqttDto mqttInbounUpDateByKey(String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        LimitQueue<String> lqueue = MqttInboundConfig.lqueue;
        MqttDto mqttDto = null;
        List<MessageDto> newmessageDtoList;
        if (lqueue.peek() != null) {
            mqttDto = JSONObject.parseObject(lqueue.peek(), MqttDto.class);
        }
        if (mqttDto != null) {
            Collection<MessageDto> messageDtoList = mqttDto.getValues();
            newmessageDtoList = messageDtoList.stream().filter(obj -> obj.getId().equals(key)).collect(Collectors.toList());
            mqttDto.setValues(deduplication(newmessageDtoList));
        }
        return mqttDto;
    }

    @Override
    public MqttDto mqttInbounUpDateByKeyArray(String[] keys) {
        LimitQueue<String> lqueue = MqttInboundConfig.lqueue;
        MqttDto mqttDto = null;
        List<MessageDto> newmessageDtoList = null;
        if (lqueue.peek() != null) {
            mqttDto = JSONObject.parseObject(lqueue.peek(), MqttDto.class);
        }
        if (mqttDto != null) {
            Collection<MessageDto> messageDtoList = mqttDto.getValues();
            for (String key : keys) {
                List<MessageDto> objList = messageDtoList.stream().filter(obj -> obj.getId().equals(key)).collect(Collectors.toList());
                if (newmessageDtoList == null) {
                    newmessageDtoList = objList;
                } else {
                    newmessageDtoList.addAll(objList);
                }
            }
        }
        mqttDto.setValues(deduplication(newmessageDtoList));
        return mqttDto;
    }

    /**
     * 写
     */
    @Override
    public void mqttOutbound(String message) {
        ApplicationContext ctx = SpringUtil.getApplicationContext();
        MqttOutboundConfig.MyGateway gateway = ctx.getBean(MqttOutboundConfig.MyGateway.class);
        gateway.sendToMqtt(message);
    }

    /**
     * 自定义建立连接
     *
     * @param host     连接地址(tcp://192.168.18.190:1883)
     * @param port     端口号 1883
     * @param username 用户名
     * @param password 密码
     */
    @Override
    public String createConnection(String host, String port, String username, String password, String topic) throws MqttException {
        MqttCallbackImpl mqttTest = new MqttCallbackImpl();
        ConnectionConfigEntity connectionConfigEntity = new ConnectionConfigEntity();
        connectionConfigEntity.delete(new QueryWrapper<ConnectionConfigEntity>().ge("is_delete", 0));
        connectionConfigEntity.setHost(host)
                .setProt(port)
                .setUsername(username)
                .setTopic(topic)
                .setPassword(password);
        connectionConfigEntity.insert();
        return setMqttClient(host, port, username, password, topic, mqttTest);
    }

    @Override
    public String getConnection() {
        return mqttClient.getServerURI();
    }

    @Override
    public void createPub(String topic, String msg, int qos) throws MqttException {
        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setQos(qos);
        mqttMessage.setPayload(msg.getBytes());
        MqttTopic mqttTopic = mqttClient.getTopic(topic);
        MqttDeliveryToken token = mqttTopic.publish(mqttMessage);
        token.waitForCompletion();
    }

    @Override
    public void createSub(String topic) throws MqttException {
        mqttClient.subscribe(topic);
    }

    @Override
    public void createClose() throws MqttException {
        mqttClient.disconnect();
        mqttClient.close();
    }

    /**
     * 去重
     */
    public Collection<MessageDto> deduplication(Collection<MessageDto> dtoList) {
        return new HashSet<>(dtoList);
    }


    /**
     * 客户端connect连接mqtt服务器
     *
     * @param userName
     * @param passWord
     * @param mqttCallback
     * @throws MqttException
     */
    public String setMqttClient(String host, String port, String userName, String passWord, String topic, MqttCallback mqttCallback) throws MqttException {
        MqttConnectOptions options = mqttConnectOptions(host + ":" + port, userName, passWord);
        if (mqttCallback == null) {
            mqttClient.setCallback(new MqttCallbackImpl());
        } else {
            mqttClient.setCallback(mqttCallback);
        }
        mqttClient.connect(options);
        mqttClient.subscribe(topic);
        return mqttClient.getServerURI();
    }

    /**
     * MQTT连接参数设置
     */
    private MqttConnectOptions mqttConnectOptions(String host, String userName, String passWord) throws MqttException {
        mqttClient = new MqttClient(host, clientId, new MemoryPersistence());
        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName(userName);
        options.setPassword(passWord.toCharArray());
        //默认：30
        options.setConnectionTimeout(10);
        //默认：false
        options.setAutomaticReconnect(false);
        //默认：true
        options.setCleanSession(false);
        options.setKeepAliveInterval(20);//默认：60
        return options;
    }

    /**
     * 关闭MQTT连接
     */
    public void close() throws MqttException {
        mqttClient.close();
        mqttClient.disconnect();
    }


}
