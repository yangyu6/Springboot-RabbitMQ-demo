package com.yu.bootrabbitcustomer.boot;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MarshallingMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * 队列1消费者
 */
@Component
public class BootConsumer {

    @Autowired
    AmqpAdmin amqpAdmin;

    /**
     * 接受指定队列的消息
     */
    @RabbitListener(queues = "boot_test_queue_1")
    public void consumer(Message message) {
        // 消息内容
        String body = new String(message.getBody());
        // 反序列化为对象 ，此处使用的fastjson 其他也可以
        JSONObject jsonObject = JSON.parseObject(body);
        System.out.println(jsonObject);
    }

    /**
     * 新建队列，交换机，绑定
     * AmqpAdmin 的用法
     */
    public void createQueueExchangeBinding() {

        //创建交换器
        amqpAdmin.declareExchange(new DirectExchange("amqpAdmin.exchange"));
        //创建队列
        amqpAdmin.declareQueue(new Queue("amqpAdmin.queue", true));
        //两种绑定方式
        //绑定
        //amqpAdmin.declareBinding(new Binding("amqpAdmin.queue", Binding.DestinationType.QUEUE,
        //        "amqpAdmin.exchange", "amqp.news", null));
        //绑定
        amqpAdmin.declareBinding(BindingBuilder.bind(new Queue("amqpAdmin.queue")).to(new TopicExchange("amqpAdmin.exchange")).with("amqp.news"));
    }
}
