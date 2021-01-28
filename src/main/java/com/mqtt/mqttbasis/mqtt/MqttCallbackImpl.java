package com.mqtt.mqttbasis.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MqttCallbackImpl implements MqttCallback {

    /**
     * 当到服务器的连接丢失时，将调用此方法。
     * @param cause 导致连接丢失的原因。
     */
    @Override
    public void connectionLost(Throwable cause) {
        System.out.println("服务器连接丢失:");
        System.out.println("原因:" + cause);
    }

    /**
     * 当消息从服务器到达时调用此方法。
     *
     * @param topic   消息上发布到的主题的名称
     * @param message 实际的消息。
     * @throws Exception
     */
    @Override
    public void messageArrived(String topic, MqttMessage message){
        System.out.println("收到服务器消息:");
        System.out.println("topic:" + topic);
        System.out.println("message:" + message);
        MqttConfig.lqueue.offer(message.toString());
    }

    /**
     * 当完成消息传递时调用，确认已收到。
     * 对于QoS 0消息，它是消息传递到网络后调用交付。
     * 对于QoS 1，在接收到PUBACK时调用。
     * 对于QoS 2，在接收到PUBCOMP时调用。
     * 令牌将与发布消息时返回的令牌相同。
     *
     * @param token 令牌将与发布消息时返回的令牌相同。
     */
    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        System.out.println("消息完成传递,确认收到:");
        System.out.println("token:" + token);
    }
}
