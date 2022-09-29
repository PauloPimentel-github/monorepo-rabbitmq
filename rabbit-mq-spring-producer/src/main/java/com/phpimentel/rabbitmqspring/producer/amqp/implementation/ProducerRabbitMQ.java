package com.phpimentel.rabbitmqspring.producer.amqp.implementation;

import com.phpimentel.rabbitmqspring.producer.amqp.AmqpProducer;
import com.phpimentel.rabbitmqspring.producer.dto.MessageQueue;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProducerRabbitMQ implements AmqpProducer<MessageQueue> {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.request.routing-key.producer}")
    private String queue;

    @Value("${spring.rabbitmq.request.exchenge.producer}")
    private String exchange;

    @Override
    public void producer(MessageQueue message) {
        try {
            this.rabbitTemplate.convertAndSend(this.exchange, this.queue, message);
        } catch (Exception exception) {
            throw new AmqpRejectAndDontRequeueException(exception);
        }
    }
}
