package com.mqtt.mqttdemo.controller;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * mqtt配置类
 */
@Data
@Component
public class MqttConfig {



    @Value("${mqttconfig.serverUrls}")
    private String serverUrls;
    private static String serverUrlsStatic;

    @Value("${mqttconfig.userName}")
    private String userName;
    private static String userNameStatic;

    @Value("${mqttconfig.password}")
    private String password;
    private static String passwordStatic;

    @Value("${mqttconfig.readTopic}")
    private String readTopic;
    private static String readTopicStatic;


    @Value("${mqttconfig.writeTopic}")
    private String writeTopic;
    private static String writeTopicStatic;


    @Value("${mqttconfig.completionTimeout}")
    private Integer completionTimeout;
    private static Integer completionTimeoutStatic;


    @Value("${mqttconfig.qos}")
    private Integer qos;
    private static Integer qosStatic;

    @Value("${mqttconfig.clientId}")
    private String clientId;

    private static String clientIdStatic;

    public MqttConfig() {
    }

    public static String getClientId() {
        return clientIdStatic;
    }



    public static String getServerUrls() {
        return serverUrlsStatic;
    }

    public static String getUserName() {
        return userNameStatic;
    }

    public static String getPassword() {
        return passwordStatic;
    }

    public static String getWriteTopic() {
        return writeTopicStatic;
    }

    public static String getReadTopic() {
        return readTopicStatic;
    }

    public static Integer getCompletionTimeout() {
        return completionTimeoutStatic;
    }

    public static Integer getQos() {
        return qosStatic;
    }


    @PostConstruct
    public void setServerUrls() {
        serverUrlsStatic = this.serverUrls;
    }

    @PostConstruct
    public void setUserName() {
        userNameStatic = this.userName;
    }

    @PostConstruct
    public void setPassword() {
        passwordStatic = this.password;
    }

    @PostConstruct
    public void setwriteTopic() {
        writeTopicStatic = this.writeTopic;
    }

    @PostConstruct
    public void setreadTopic() {
        readTopicStatic = this.readTopic;
    }

    @PostConstruct
    public void setCompletionTimeout() {
        completionTimeoutStatic = this.completionTimeout;
    }

    @PostConstruct
    public void setQos() {
        qosStatic = this.qos;
    }

    @PostConstruct
    public void setClientId() {
        clientIdStatic = this.clientId;
    }

}
