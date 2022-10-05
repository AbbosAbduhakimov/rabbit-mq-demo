package com.example.service;


import com.example.exception.ResourceNotFoundException;
import com.example.model.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ConsumerMQService {


    @Autowired
    private TransactionService transactionService;

    @RabbitListener(queues = "${spring.rabbitmq.queue1}")
    public void createQueueListener(Transaction transaction) {
        Transaction save = transactionService.save(transaction);
        log.info("saved transaction {}", save);
    }


    @RabbitListener(queues = "${spring.rabbitmq.queue2}", containerFactory = "simpleRabbitListenerContainerFactory")
    public void auditQueueListener(Integer id) {
//        Thread.sleep(1000);
        Transaction target = transactionService.findById(id);
        log.info("target transaction {}", target);
    }


    @RabbitListener(queues = "${spring.rabbitmq.queue3}")
    public void editQueueListener(Transaction transaction) {
//        Thread.sleep(1000);
        if (transaction.getId() == null){
            throw new ResourceNotFoundException("Transaction by given id not found");
        }
        transactionService.update(transaction.getId(), transaction);
        log.info("update {} transaction with this is id",transaction.getId());
    }


    @RabbitListener(queues = "${spring.rabbitmq.queue4}")
    public void deleteQueueListener(Integer id) {
        transactionService.delete(id);
        log.info("delete {} transaction with this is id", id);
    }
}

