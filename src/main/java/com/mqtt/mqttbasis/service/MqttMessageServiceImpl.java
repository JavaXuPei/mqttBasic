package com.mqtt.mqttbasis.service;

import com.alibaba.fastjson.JSONObject;
import com.mqtt.mqttbasis.controller.MqttOutboundConfig;
import com.mqtt.mqttbasis.dto.MqttDto;
import com.mqtt.mqttbasis.controller.MqttInboundConfig;
import com.mqtt.mqttbasis.dto.MessageDto;
import com.mqtt.mqttbasis.official.LimitQueue;
import com.mqtt.mqttbasis.official.SpringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MqttMessageServiceImpl implements MqttMessageService {

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
     * 去重
     */
    public Collection<MessageDto> deduplication(Collection<MessageDto> dtoList) {
        return new HashSet<>(dtoList);
    }

}
