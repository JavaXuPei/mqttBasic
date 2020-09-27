package com.mqtt.mqttbasis.controller;

import com.mqtt.mqttbasis.official.LimitQueue;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

/**
 * The following Spring Boot application shows an example of how to configure the inbound adapter with Java configuration:
 * 以下Spring Boot应用程序显示了如何使用Java配置来配置入站适配器的示例：
 *
 * @author bjy
 */
@Configuration
public class MqttInboundConfig {

    public static LimitQueue<String> lqueue = new LimitQueue<>(10);

    @Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }


    @Bean
    public MqttPahoClientFactory mqttClientFactory1() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        MqttConnectOptions options = new MqttConnectOptions();
        options.setServerURIs(new String[]{MqttConfig.getServerUrls()});
        options.setUserName(MqttConfig.getUserName());
        options.setPassword(MqttConfig.getPassword().toCharArray());
        factory.setConnectionOptions(options);
        // 自动重连
        //options.setAutomaticReconnect(true);
        // 设置为True则启用清理会话
        // options.setCleanSession(false);
        return factory;
    }


    @Bean
    public MessageProducer inbound() {
        MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter(MqttConfig.getServerUrls(), MqttConfig.getClientId(),
                        mqttClientFactory1(), MqttConfig.getReadTopic());
        adapter.setCompletionTimeout(MqttConfig.getCompletionTimeout());
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(MqttConfig.getQos());
        adapter.setOutputChannel(mqttInputChannel());
        return adapter;
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public MessageHandler handler() {
        return new MessageHandler() {
            @Override
            public void handleMessage(Message<?> message) throws MessagingException {
                lqueue.offer(message.getPayload().toString());
            }
        };
    }
}
