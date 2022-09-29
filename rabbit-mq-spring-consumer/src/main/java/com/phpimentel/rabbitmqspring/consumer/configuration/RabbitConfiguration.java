package com.phpimentel.rabbitmqspring.consumer.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 * Classe responsável por fazer a configuração do Rabbit
 */
@Configuration
public class RabbitConfiguration {

    @Autowired
    private ConnectionFactory connectionFactory;

    /**
     * Método responsável por configurar a fabrica de listerner do rabbit
     * @return SimpleRabbitListenerContainerFactory
     */
    @Bean
    SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        //setar uma connectionFactory
        factory.setConnectionFactory(this.connectionFactory);
        //setar a tipo de conversão de mensagem
        factory.setMessageConverter(this.jacksonConverter());
        return factory;
    }

    /**
     * Método responsável por criar o RabbitTemplate
     * @return RabbitTemplate
     */
    @Bean
    RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(this.connectionFactory);
        rabbitTemplate.setMessageConverter(this.jacksonConverter());
        return rabbitTemplate;
    }

    /**
     * Método responsável por fazer a conversão do tipo de mensagem
     * @return Jackson2JsonMessageConverter
     */
    @Bean
    Jackson2JsonMessageConverter jacksonConverter() {
        final ObjectMapper mapper = Jackson2ObjectMapperBuilder
                .json()
                .modules(new JavaTimeModule())
                .dateFormat(new StdDateFormat())
                .build();

        return new Jackson2JsonMessageConverter(mapper);
    }
}
