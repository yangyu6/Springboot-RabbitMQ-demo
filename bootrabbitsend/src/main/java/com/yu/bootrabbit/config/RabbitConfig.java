package com.yu.bootrabbit.config;

import com.yu.bootrabbit.boot.BootSend;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.channels.Pipe;

@Configuration
@Slf4j
public class RabbitConfig {

    @Autowired
    ConnectionFactory connectionFactory;


    @Bean
    public RabbitTemplate rabbitTemplateJson() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        // 消息发送失败返回到队列中, yml需要配置 publisher-returns: true
        rabbitTemplate.setMandatory(true);

        // 消息返回, yml需要配置 publisher-returns: true
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            String correlationId = message.getMessageProperties().getCorrelationId();
            log.debug("消息：{} 发送失败, 应答码：{} 原因：{} 交换机: {}  路由键: {}", correlationId, replyCode, replyText, exchange, routingKey);
        });

        // 消息确认, yml需要配置 publisher-confirms: true
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                log.debug("消息发送到exchange成功,id: {}", correlationData.getId());
            } else {
                log.debug("消息发送到exchange失败,原因: {}", cause);
            }
        });

        return rabbitTemplate;
    }



    /**
     * 测试队列1
     *
     * @return
     */
    @Bean
    public Queue boot_test_queue_1() {
        return new Queue("boot_test_queue_1");
    }


    /**
     * 主体模式的交换机
     *
     * @return
     */
    @Bean
    public TopicExchange boot_exchange_topic_1() {
        return new TopicExchange("boot_exchange_topic_1");
    }


    /**
     * 队列，交换机绑定
     *
     * @return
     */
    @Bean
    public Binding topic_1_queue() {
        return BindingBuilder.bind(boot_test_queue_1())
                .to(boot_exchange_topic_1())
                .with("insert");
    }
}
