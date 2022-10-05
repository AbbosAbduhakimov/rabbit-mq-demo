package com.example.service;

import com.example.exception.ResourceNotFoundException;
import com.example.model.Transaction;
import com.example.repository.TransactionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@Transactional
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;


    public Transaction save(Transaction transaction){
        Transaction preserve = new Transaction();
        preserve.setAmount(transaction.getAmount());
        preserve.setSentFrom(transaction.getSentFrom());
        preserve.setSentTo(transaction.getSentTo());
        log.info("saving transaction : {}",preserve);
        return transactionRepository.save(preserve);
    }


    @Transactional(readOnly = true)
    public Transaction findById(Integer id){
        return transactionRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Transaction not found"));
    }

    @Transactional(readOnly = true)
    public Iterable<Transaction> findAll(){
        return transactionRepository.findAll();
    }


    public void delete(Integer id){
        transactionRepository.delete(this.findById(id));
    }


    public void update(Integer transactionId, Transaction transaction){
        Transaction update = this.findById(transactionId);
        update.setSentTo(transaction.getSentTo());
        update.setAmount(transaction.getAmount());
        transactionRepository.flush();
    }


}
