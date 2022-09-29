package com.phpimentel.rabbitmqspring.producer.configuration;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProducerRabbitConfiguration {

    @Value("${spring.rabbitmq.request.routing-key.producer}")
    private String queue;

    @Value("${spring.rabbitmq.request.exchenge.producer}")
    private String exchange;

    @Value("${spring.rabbitmq.request.dead-letter.producer}")
    private String deadLetter;

    @Value("${spring.rabbitmq.request.parking-lot.producer}")
    private String parkingLot;

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(this.exchange);
    }

    /**
     * Método ciar a deadLetter
     * @return QueueBuilder
     */
    @Bean
    Queue deadLetter() {
        return QueueBuilder.durable(this.deadLetter)
                .deadLetterExchange(this.exchange)
                .deadLetterRoutingKey(this.queue)
                .build();
    }

    /**
     * Método para criar a queue
     * @return Queue
     */
    @Bean
    Queue queue() {
        return QueueBuilder.durable(this.queue)
                .deadLetterExchange(this.exchange)
                .deadLetterRoutingKey(this.deadLetter)
                .build();
    }

    /**
     * Método para cirar o parkinLot
     * @return Queue
     */
    @Bean
    Queue parkingLot() {
        return new Queue(this.parkingLot);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(this.queue())
                .to(this.exchange())
                .with(this.queue);
    }

    @Bean
    public Binding bindingDeadLetter() {
        return BindingBuilder.bind(this.deadLetter())
                .to(this.exchange())
                .with(this.deadLetter);
    }

    @Bean
    public Binding bindingParkinLot() {
        return BindingBuilder.bind(this.parkingLot())
                .to(this.exchange())
                .with(this.parkingLot);
    }
}
