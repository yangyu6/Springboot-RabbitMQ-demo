package com.yu.bootrabbitcustomer;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit
public class BootrabbitcustomerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootrabbitcustomerApplication.class, args);
    }

}
