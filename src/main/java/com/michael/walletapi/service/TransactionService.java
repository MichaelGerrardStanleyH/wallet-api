package com.michael.walletapi.service;

import com.michael.walletapi.model.Transaction;
import com.michael.walletapi.model.TransactionType;
import com.michael.walletapi.model.User;
import com.michael.walletapi.model.Wallet;
import com.michael.walletapi.model.dto.TransactionDTO;

import java.time.LocalDateTime;

public interface TransactionService {
    public LocalDateTime getTimeNow();

    public Transaction getUsersTransactionById(Long transactionId, Wallet wallet);

    public Transaction createTopupTransaction(Wallet existWallet, TransactionDTO transactionDTO);

    public Transaction createTransferTransaction(Wallet existWalletSender, Wallet existWalletRecipient, TransactionDTO transactionDTO);
}
