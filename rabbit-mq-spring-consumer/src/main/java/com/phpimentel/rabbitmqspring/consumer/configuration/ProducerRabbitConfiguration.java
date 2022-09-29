package com.phpimentel.rabbitmqspring.consumer.configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Classe responsável por fazer a configuração do Rabbit
 * Configuração producer
 */
@Configuration
public class ProducerRabbitConfiguration {

    /*** Atributos da Classe ***/

    @Value("${spring.rabbitmq.request.exchenge.producer}")
    private String exchange;

    @Value("${spring.rabbitmq.request.routing-key.producer}")
    private String rountingKey;

    @Value("${spring.rabbitmq.request.dead-letter.producer}")
    private String deadLetter;

    @Value("${spring.rabbitmq.request.parking-lot.producer}")
    private String parkingLot;

    /* Métodos da Classe */

    /**
     * Método responsável por criar a fila principal
     * @return Queue
     */
    @Bean
    Queue queue() {
        return QueueBuilder.durable(this.rountingKey)
                .deadLetterExchange(this.exchange)
                .deadLetterRoutingKey(this.deadLetter)
                .build();
    }

    /**
     * Método responsável por criar a fila de deadLetter
     * @return Queue
     */
    @Bean
    Queue queueDeadLetter() {
        return QueueBuilder.durable(this.deadLetter)
                .deadLetterExchange(this.exchange)
                .deadLetterRoutingKey(this.rountingKey)
                .build();
    }

    /**
     * Método responsável por criar a fila de parkingLot
     * @return Queue
     */
    @Bean
    Queue queueParkingLot() {
        return new Queue(this.parkingLot);
    }

    /**
     * Método responsável criar uma exchange
     * Com este método é possível fazer o binding de uma fila ao exchange
     * @return DirectExchange
     */
    @Bean
    DirectExchange directExchange() {
        return new DirectExchange(this.exchange);
    }

    /**
     * Método responsável por associar uma fila a um exchange
     * @return Binding
     */
    @Bean
    Binding bindingQueue() {
        return BindingBuilder.bind(this.queue())
                .to(this.directExchange())
                .with(this.rountingKey);
    }

    /**
     * Método responsável por associar uma fila de deadLetter a um exchange
     * @return Binding
     */
    @Bean
    Binding bindingDeadLetter() {
        return BindingBuilder.bind(this.queueDeadLetter())
                .to(this.directExchange())
                .with(this.deadLetter);
    }

    /**
     * Método responsável por associar uma fila de parkingLot a um exchange
     * @return Binding
     */
    @Bean
    Binding bindingParkingLot() {
        return BindingBuilder.bind(this.queueParkingLot())
                .to(this.directExchange())
                .with(this.parkingLot);
    }
}
