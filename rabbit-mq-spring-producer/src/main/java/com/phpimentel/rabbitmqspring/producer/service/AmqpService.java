package com.phpimentel.rabbitmqspring.producer.service;

import com.phpimentel.rabbitmqspring.producer.dto.MessageQueue;

public interface AmqpService {

    void sendToConsumer(MessageQueue message);
}
