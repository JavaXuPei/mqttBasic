package com.mqtt.mqttbasis.controller;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

/**
 * The following Spring Boot application show an example of how to configure the outbound adapter with Java configuration:
 *  以下Spring Boot应用程序显示了如何使用Java配置来配置出站适配器的示例：
 * @author bjy
 */

@Configuration
@IntegrationComponentScan
public class MqttOutboundConfig {
    @Bean
    public MqttPahoClientFactory mqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        MqttConnectOptions options = new MqttConnectOptions();
        options.setServerURIs(new String[]{MqttConfig.getServerUrls()});
        options.setUserName(MqttConfig.getUserName());
        options.setPassword(MqttConfig.getPassword().toCharArray());
        factory.setConnectionOptions(options);
        return factory;
    }
    @Bean
    @ServiceActivator(inputChannel = "mqttOutboundChannel")
    public MessageHandler mqttOutbound() {
        MqttPahoMessageHandler messageHandler =
                new MqttPahoMessageHandler(MqttConfig.getClientId(), mqttClientFactory());
        messageHandler.setAsync(true);
        // 写入的topic
        messageHandler.setDefaultTopic(MqttConfig.getWriteTopic());
        return messageHandler;
    }
    @Bean
    public MessageChannel mqttOutboundChannel() {
        return new DirectChannel();
    }
    @MessagingGateway(defaultRequestChannel = "mqttOutboundChannel")
    public interface MyGateway {
        void sendToMqtt(String data);
    }
}