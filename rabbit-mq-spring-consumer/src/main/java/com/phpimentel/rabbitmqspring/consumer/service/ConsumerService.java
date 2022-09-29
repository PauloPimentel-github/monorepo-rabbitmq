package com.phpimentel.rabbitmqspring.consumer.service;

import com.phpimentel.rabbitmqspring.consumer.dto.MessageQueue;

public interface ConsumerService {

    void action(MessageQueue message);
}
