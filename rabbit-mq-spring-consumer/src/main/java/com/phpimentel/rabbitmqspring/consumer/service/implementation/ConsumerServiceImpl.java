package com.phpimentel.rabbitmqspring.consumer.service.implementation;

import com.phpimentel.rabbitmqspring.consumer.dto.MessageQueue;
import com.phpimentel.rabbitmqspring.consumer.service.ConsumerService;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.stereotype.Service;

@Service
public class ConsumerServiceImpl implements ConsumerService {

    @Override
    public void action(MessageQueue message) {
        if ("teste".equalsIgnoreCase(message.getText())) {
            throw new AmqpRejectAndDontRequeueException("error");
        }

        System.out.println("RabbitMQ Consumer:" + message.getText());
    }
}
