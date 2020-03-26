package com.yu.myrabbit.service;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class MySendRouting {

    // 交换机名称
    private final static String EXCHANGE_NAME = "test_exchange_topic";

    public static void main(String[] argv) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        // 声明exchange 并设置 类型
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");

        // 消息内容
        String message = "Hello World!";
        // 发送消息到 指定交换机 ， routingkey 为 delete
        channel.basicPublish(EXCHANGE_NAME, "delete", null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");
        channel.close();
        connection.close();

    }
}
