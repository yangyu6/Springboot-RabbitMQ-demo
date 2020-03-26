package com.yu.bootrabbit.boot;


import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class BootSend {


    @Autowired
    private RabbitTemplate rabbitTemplate;


    public void send(String message) {
        // 添加消息转换为 json序列化方式
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = dateFormat.format(new Date());
        // 封装消息
        Map<String, Object> params = new HashMap<>();
        params.put("name", "大米饭");
        params.put("age", 23);
        params.put("date", format);
        params.put("dates", new Date());
        params.put("message", message);
        // 序列化消息并发送到指定交换机
        rabbitTemplate.convertAndSend("exchangge_normal", "key", params);
        System.out.println(message + format);
    }

    public void send2() {
        System.out.println(rabbitTemplate.getExchange());
    }

}
