package com.example.rest;

import com.example.model.Transaction;
import com.example.service.TransactionService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TransactionController {

    private final TransactionService transactionService;
    private final RabbitTemplate rabbitTemplate;


    @Autowired
    public TransactionController(TransactionService transactionService, RabbitTemplate rabbitTemplate) {
        this.transactionService = transactionService;
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping
    public void create(@RequestBody Transaction transaction){
    }




}
