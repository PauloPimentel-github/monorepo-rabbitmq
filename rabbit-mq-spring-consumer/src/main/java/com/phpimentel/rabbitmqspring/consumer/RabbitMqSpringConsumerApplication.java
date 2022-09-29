package com.phpimentel.rabbitmqspring.consumer;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableRabbit
@SpringBootApplication
@ComponentScan("com.phpimentel.rabbitmqspring")
public class RabbitMqSpringConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitMqSpringConsumerApplication.class, args);
    }

}
