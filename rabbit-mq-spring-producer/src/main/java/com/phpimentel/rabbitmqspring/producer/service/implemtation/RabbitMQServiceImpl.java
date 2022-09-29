package com.phpimentel.rabbitmqspring.producer.service.implemtation;

import com.phpimentel.rabbitmqspring.producer.amqp.AmqpProducer;
import com.phpimentel.rabbitmqspring.producer.dto.MessageQueue;
import com.phpimentel.rabbitmqspring.producer.service.AmqpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQServiceImpl implements AmqpService {

    @Autowired
    private AmqpProducer<MessageQueue> amqp;

    @Override
    public void sendToConsumer(MessageQueue message) {
        this.amqp.producer(message);
    }
}
