package com.phpimentel.rabbitmqspring.producer.configuration;

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
 * Classe de configuracao do RabbitMQ
 */
@Configuration
public class RabbitConfiguration {

    /** cria fabrica de conexao com o rabbitMQ */
    @Autowired
    private ConnectionFactory connectionFactory;


    /**
     * bean para o factory
     * @return SimpleRabbitListenerContainerFactory
     */
    @Bean
    public SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(this.connectionFactory);
        factory.setMessageConverter(this.jacksonConverter());
        return factory;
    }

    /**
     * bean para o rabbit template
     * @return RabbitTemplate
     */
    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(this.connectionFactory);
        rabbitTemplate.setMessageConverter(this.jacksonConverter());
        return rabbitTemplate;
    }

    /**
     * bean para o Jackson2JsonMessageConverter
     * cria um padrao de convesao
     * @return Jackson2JsonMessageConverter
     */
    @Bean
    Jackson2JsonMessageConverter jacksonConverter() {
        final ObjectMapper mapper = Jackson2ObjectMapperBuilder
                .json()
                .modules(new JavaTimeModule())
                .dateFormat(new StdDateFormat())
                .build();
        return new Jackson2JsonMessageConverter();
    }
}
