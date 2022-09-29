package com.phpimentel.rabbitmqspring.consumer.dto;

import lombok.Data;

@Data
public class MessageQueue {

    private String text;

    private Pessoa pessoa;

}
