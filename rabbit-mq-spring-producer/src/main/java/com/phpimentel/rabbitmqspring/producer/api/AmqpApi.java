package com.phpimentel.rabbitmqspring.producer.api;

import com.phpimentel.rabbitmqspring.producer.dto.MessageQueue;
import com.phpimentel.rabbitmqspring.producer.service.AmqpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rabbitMQ")
public class AmqpApi {

    @Autowired
    private AmqpService service;

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("/send")
    public void sendToConsumer(@RequestBody MessageQueue message) {
        this.service.sendToConsumer(message);
    }
}
