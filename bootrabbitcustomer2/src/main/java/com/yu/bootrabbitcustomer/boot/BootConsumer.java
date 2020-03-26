package com.yu.bootrabbitcustomer.boot;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MarshallingMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * 队列1消费者
 */
@Slf4j
@Component
public class BootConsumer {

    @Autowired
    AmqpAdmin amqpAdmin;
    @Autowired
    RabbitTemplate rabbitTemplate;


    /**
     * 接受指定队列的消息
     */
    @RabbitListener(queues = {"queue_normal"})
    public void consumer2(Message message, Channel channel) throws IOException {
        // 消息内容
        String body = new String(message.getBody());
        log.error(body);
        int i = 1 / 0;
        // 反序列化为对象 ，此处使用的fastjson 其他也可以
        JSONObject jsonObject = JSON.parseObject(body);
        System.out.println(jsonObject);
    }


    /**
     * 接受指定队列的消息
     */

    public void consumer(Message message, Channel channel) throws IOException {
        //channel.messageCount("test_queue_direct2");
        //channel.
        MessageProperties messageProperties = message.getMessageProperties();
        // 获取转发重试次数
        List<Map<String, ?>> xDeathHeader = messageProperties.getXDeathHeader();
        String body = "";
        try {
            // 消息内容
            body = new String(message.getBody());
            int i = 1 / 0;
            // 反序列化为对象 ，此处使用的fastjson 其他也可以
            JSONObject jsonObject = JSON.parseObject(body);
            System.out.println(jsonObject);
        } catch (Exception e) {
            // 重试次数大于3次 ，丢入死信队列
            if (xDeathHeader != null && new Integer(xDeathHeader.get(0).get("count") + "") >= 3) {
                rabbitTemplate.convertAndSend("exchangge_retry", "key", body);
                //channel.basicAck(messageProperties.getDeliveryTag(),true);
            } else {
                // 拒绝消息
                channel.basicReject(messageProperties.getDeliveryTag(), false);
            }
        }

    }

}
