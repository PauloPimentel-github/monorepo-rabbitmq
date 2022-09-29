package com.phpimentel.rabbitmqspring.producer.dto;

import lombok.Data;

@Data
public class MessageQueue {

    private String text;

   private Pessoa pessoa;
}
