package com.yu.myrabbit.boot;


import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class BootSend {
    private final RabbitTemplate rabbitTemplate;

    public BootSend(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send(){
        rabbitTemplate.convertAndSend("boot_test_queue_1","haahshan");
        System.out.println("hahashan");
    }

}
