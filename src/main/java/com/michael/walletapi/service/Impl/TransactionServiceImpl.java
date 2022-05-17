package com.michael.walletapi.service.Impl;

import com.michael.walletapi.model.Transaction;
import com.michael.walletapi.model.TransactionType;
import com.michael.walletapi.model.User;
import com.michael.walletapi.model.Wallet;
import com.michael.walletapi.model.dto.TransactionDTO;
import com.michael.walletapi.repository.TransactionRepository;
import com.michael.walletapi.service.TransactionService;
import com.michael.walletapi.service.TransactionTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    TransactionTypeService transactionTypeService;

    public LocalDateTime getTimeNow(){
        return LocalDateTime.now();
    }

    public Transaction getUsersTransactionById(Long transactionId,Wallet wallet){
        return this.transactionRepository.findByIdAndWallet(transactionId, wallet);
    }

    public Transaction createTopupTransaction(Wallet existWallet, TransactionDTO transactionDTO){
        TransactionType existType = this.transactionTypeService.getTransactionTypeById(transactionDTO.getTransaction_type_id());

        if(existType == null){
            return null;
        }

        Transaction newTransaction = Transaction.builder()
                .amount(transactionDTO.getAmount())
                .transactionType(existType)
                .description(transactionDTO.getDescription())
                .toWallet(existWallet.getId())
                .wallet(existWallet)
                .created_at(getTimeNow())
                .build();

        return this.transactionRepository.save(newTransaction);
    }

    public Transaction createTransferTransaction(Wallet existWalletSender, Wallet existWalletRecipient, TransactionDTO transactionDTO) {
        TransactionType existType = this.transactionTypeService.getTransactionTypeById(transactionDTO.getTransaction_type_id());

        if(existType == null){
            return null;
        }

        Transaction newTransactionSender = Transaction.builder()
                .amount(transactionDTO.getAmount())
                .transactionType(existType)
                .description(transactionDTO.getDescription())
                .fromWallet(existWalletSender.getId())
                .toWallet(existWalletRecipient.getId())
                .wallet(existWalletSender)
                .created_at(getTimeNow())
                .build();

        Transaction newTransactionRecipient = Transaction.builder()
                .amount(transactionDTO.getAmount())
                .transactionType(existType)
                .description(transactionDTO.getDescription())
                .fromWallet(existWalletSender.getId())
                .toWallet(existWalletRecipient.getId())
                .wallet(existWalletRecipient)
                .created_at(getTimeNow())
                .build();

        this.transactionRepository.save(newTransactionRecipient);

        return this.transactionRepository.save(newTransactionSender);
    }
}
