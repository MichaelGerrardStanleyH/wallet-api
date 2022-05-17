package com.michael.walletapi.service;

import com.michael.walletapi.model.Transaction;
import com.michael.walletapi.model.User;
import com.michael.walletapi.model.Wallet;
import com.michael.walletapi.model.dto.TransactionDTO;
import com.michael.walletapi.model.dto.WalletDTO;

import java.time.LocalDateTime;

public interface WalletService {
    public LocalDateTime getTimeNow();

    public Wallet saveWallet(Wallet wallet);

    public Wallet getWalletById(Long id);

    public Wallet createWallet(User savedUser);

    public void deleteWalletById(Long id);

    public Wallet addWallet(User existUser, WalletDTO walletDTO);

    public Transaction topUp(Long walletId, TransactionDTO transactionDTO);

    public Transaction transfer(Long walletId, TransactionDTO transactionDTO);

    public Wallet updateUserWallet(Long walletId, WalletDTO walletDTO);

    public Wallet getUsersWallet(Long walletId, User existUser);
}
