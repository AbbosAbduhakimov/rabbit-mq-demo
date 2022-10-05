package com.example.service;

import com.example.model.Transaction;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ProducerMQService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.messageExchange}")
    private String exchange;


    public void sendTransaction(Transaction transaction,String routingKey){
        rabbitTemplate.convertAndSend(exchange,routingKey,transaction);
    }

    public void sendId(Integer id,String routingKey){
        rabbitTemplate.convertAndSend(exchange,routingKey,id);
    }

}
