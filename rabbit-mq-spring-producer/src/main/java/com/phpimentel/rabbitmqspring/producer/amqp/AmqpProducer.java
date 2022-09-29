package com.phpimentel.rabbitmqspring.producer.amqp;

public interface AmqpProducer<T> {

    void producer(T t);
}
