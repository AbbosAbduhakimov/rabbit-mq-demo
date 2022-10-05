package com.example.controller;

import com.example.model.Transaction;
import com.example.service.ProducerMQService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transaction")
@Slf4j
@Api(value = "Transaction endpoint documentation")
public class TransactionController {

    private final ProducerMQService producerMQService;

    @Value("${app.message}")
    private String message;

    @Autowired
    public TransactionController(ProducerMQService producerMQService) {
        this.producerMQService = producerMQService;
    }




    @PostMapping
    @ApiOperation(value = "create transaction method")
    public String create(@RequestBody Transaction transaction){
        producerMQService.sendTransaction(transaction,"transaction.create");
        log.info("transaction:create send rabbitmq {}",transaction);
        return message;
    }


    @GetMapping("/{id}")
    @ApiOperation(value = "get transaction method")
    public String get(@PathVariable("id") Integer id){
        producerMQService.sendId(id,"transaction.audit");
        log.info("transaction:audit send rabbitmq {}",id);
        return message;
    }

    @PutMapping
    @ApiOperation(value = "edit transaction method")
    public String update(@RequestBody Transaction transaction){
        producerMQService.sendTransaction(transaction,"transaction.edit");
        log.info("transaction:edit send rabbitmq {}",transaction.getId());
        return message;
    }


    @DeleteMapping("/{id}")
    @ApiOperation(value = "delete transaction method")
    public String delete(@PathVariable("id") Integer id){
        producerMQService.sendId(id,"transaction.delete");
        log.info("transaction:edit send rabbitmq {}",id);
        return message;
    }

}
