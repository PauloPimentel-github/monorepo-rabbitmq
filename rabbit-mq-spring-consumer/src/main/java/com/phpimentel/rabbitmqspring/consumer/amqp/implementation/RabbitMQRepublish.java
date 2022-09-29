package com.phpimentel.rabbitmqspring.consumer.amqp.implementation;

import com.phpimentel.rabbitmqspring.consumer.amqp.AmqpRepublish;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Classe responsável pelo republish de mensagens
 */
@Component
public class RabbitMQRepublish implements AmqpRepublish {

    private static final String X_RETRIES_HEADER = "x-retries";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.request.exchenge.producer}")
    private String exchange;

    @Value("${spring.rabbitmq.request.routing-key.producer}")
    private String queue;

    @Value("${spring.rabbitmq.request.dead-letter.producer}")
    private String deadLetter;

    @Value("${spring.rabbitmq.request.parking-lot.producer}")
    private String parkingLot;

    /**
     * Método responsável por fazer o republish de uma mensagem
     */
    @Scheduled(cron = "${spring.rabbitmq.listener.time-retry}")
    @Override
    public void republish() {
        List<Message> listMessages = this.getQueueMessages();

        listMessages.forEach(message ->  {
            Map<String, Object> headers = message.getMessageProperties().getHeaders();
            Integer retriesHeader = (Integer) headers.get(X_RETRIES_HEADER);

            if (Objects.isNull(retriesHeader)) {
                retriesHeader = 0;
            }

            if (retriesHeader < 3) {
                headers.put(X_RETRIES_HEADER, retriesHeader + 1);
                this.rabbitTemplate.send(this.exchange, this.queue, message);
            } else {
                this.rabbitTemplate.send(this.parkingLot, message);
            }
        });
    }

    /**
     * Método responsável por acessar a fila de deadLetter
     * Faz a leitura de item por item e cria uma lista com todas a mensagens que existem
     * Vai retornar todas a mensagens prontas
     * @return List<Message>
     */
    private List<Message> getQueueMessages() {
        List<Message> listMessages = new ArrayList<>();
        boolean isNull = false;
        Message message;

        do {
            message = this.rabbitTemplate.receive(this.deadLetter);
            isNull = Objects.nonNull(message);

            if (Boolean.TRUE.equals(isNull)) {
                listMessages.add(message);
            }

        } while (Boolean.TRUE.equals(isNull));

        return listMessages;
    }
}
