package com.phpimentel.rabbitmqspring.consumer.service.implementation;

import com.phpimentel.rabbitmqspring.consumer.amqp.AmqpRepublish;
import com.phpimentel.rabbitmqspring.consumer.service.RepublishSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RepublishSeviceImpl implements RepublishSevice {

    @Autowired
    private AmqpRepublish republish;

    @Override
    public void repost() {
        this.republish.republish();
    }
}
