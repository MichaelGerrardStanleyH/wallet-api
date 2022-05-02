package com.michael.walletapi.service;

import com.michael.walletapi.model.Transaction;
import com.michael.walletapi.model.TransactionType;
import com.michael.walletapi.model.Wallet;
import com.michael.walletapi.model.dto.TransactionDTO;
import com.michael.walletapi.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TransactionServiceImpl {
    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    TransactionTypeServiceImpl transactionTypeService;

    public LocalDateTime getTimeNow(){
        return LocalDateTime.now();
    }

    public Transaction createTransaction(Wallet existWallet, TransactionDTO transactionDTO){
        TransactionType existType = this.transactionTypeService.getTransactionTypeById((long) transactionDTO.getTransaction_type_id());

        Transaction newTransaction = Transaction.builder()
                .amount(transactionDTO.getAmount())
                .transactionType(existType)
                .description(transactionDTO.getDescription())
                .wallet(existWallet)
                .created_at(getTimeNow())
                .build();

        return this.transactionRepository.save(newTransaction);
    }
}
