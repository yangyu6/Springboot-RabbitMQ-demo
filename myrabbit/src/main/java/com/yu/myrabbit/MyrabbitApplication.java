package com.yu.myrabbit;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit
public class MyrabbitApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyrabbitApplication.class, args);
    }

}
