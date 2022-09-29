package com.phpimentel.rabbitmqspring.consumer.amqp.implementation;

import com.phpimentel.rabbitmqspring.consumer.amqp.AmqpConsumer;
import com.phpimentel.rabbitmqspring.consumer.dto.MessageQueue;
import com.phpimentel.rabbitmqspring.consumer.service.ConsumerService;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQConsumer implements AmqpConsumer<MessageQueue> {

    @Autowired
    private ConsumerService consumerService;

    @Override
    @RabbitListener(queues = "${spring.rabbitmq.request.routing-key.producer}")
    public void consumer(MessageQueue message) {
        try {
            this.consumerService.action(message);
        } catch (Exception exception) {
            throw new AmqpRejectAndDontRequeueException(exception);
        }
    }
}
