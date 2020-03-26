package com.yu.myrabbit.config;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    public Queue boot_test_queue_1(){
        return  new Queue("boot_test_queue_1");
    }



    /*@Bean
    public ExchangeBuilder boot_exchange_topic_1(){
        return new ExchangeBuilder("boot_exchange_topic_1","topic");
    }*/
}
