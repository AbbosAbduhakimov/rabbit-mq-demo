package com.example.config;


import com.example.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class ConsumerConfigure {

    @Autowired
    private TransactionService transactionService;

    @Value("${spring.rabbitmq.queue1}")
    private String createTransactionQueue;

    @Value("${spring.rabbitmq.queue2}")
    private String auditTransactionQueue;

    @Value("${spring.rabbitmq.queue3}")
    private String editTransactionQueue;

    @Value("${spring.rabbitmq.queue4}")
    private String deleteTransactionQueue;

    @Value("${spring.rabbitmq.messageExchange}")
    private String messageExchange;

    @Value("${spring.rabbitmq.transactionExchange}")
    private String transactionExchange;


    @Bean
    public Queue createQueue() {
        return new Queue(createTransactionQueue, true);
    }


    @Bean
    public Queue auditQueue() {
        return new Queue(auditTransactionQueue, true);
    }


    @Bean
    public Queue editQueue() {
        return new Queue(editTransactionQueue, true);
    }


    @Bean
    public Queue deleteQueue() {
        return new Queue(deleteTransactionQueue, true);
    }

    @Bean
    public Exchange messageExchange() {
        return ExchangeBuilder.topicExchange(messageExchange).durable(true).build();
    }


    @Bean
    public Exchange transactionExchange() {
        return ExchangeBuilder.topicExchange(transactionExchange).durable(true).build();
    }


    @Bean
    public Declarables bindings() {
        return new Declarables(
                BindingBuilder
                        .bind(createQueue())
                        .to(transactionExchange())
                        .with("#.create")
                        .noargs(),
                BindingBuilder
                        .bind(auditQueue())
                        .to(transactionExchange())
                        .with("#.audit")
                        .noargs(),
                BindingBuilder
                        .bind(editQueue())
                        .to(transactionExchange())
                        .with("#.edit")
                        .noargs(),
                BindingBuilder
                        .bind(deleteQueue())
                        .to(transactionExchange())
                        .with("#.delete")
                        .noargs(),
                BindingBuilder
                        .bind(transactionExchange())
                        .to(messageExchange())
                        .with("transaction.*")
                        .noargs()
        );
    }

}



