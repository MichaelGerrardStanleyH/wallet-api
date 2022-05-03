package com.michael.walletapi.service;

import com.michael.walletapi.model.User;
import com.michael.walletapi.model.Wallet;
import com.michael.walletapi.model.dto.TransactionDTO;
import com.michael.walletapi.model.dto.WalletDTO;
import com.michael.walletapi.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class WalletServiceImpl {
    @Autowired
    WalletRepository walletRepository;

    @Autowired
    TransactionServiceImpl transactionService;

    public LocalDateTime getTimeNow(){
        return LocalDateTime.now();
    }

    public Wallet createWallet(User savedUser) {
        Wallet newWallet = Wallet.builder()
                .name("Kantong Utama")
                .balance(0)
                .created_at(getTimeNow())
                .user(savedUser)
                .build();

        return this.walletRepository.save(newWallet);
    }

    public Wallet addWallet(User existUser, WalletDTO walletDTO) {
        Wallet newWallet = Wallet.builder()
                .name(walletDTO.getName())
                .user(existUser)
                .created_at(getTimeNow())
                .build();

        return this.walletRepository.save(newWallet);
    }

    public void topUp(Long walletId, TransactionDTO transactionDTO) {
        Wallet existWallet = this.walletRepository.getById(walletId);

        this.transactionService.createTopupTransaction(existWallet, transactionDTO);

        existWallet.setBalance(existWallet.getBalance() + transactionDTO.getAmount());

        this.walletRepository.save(existWallet);
    }

    public void transfer(Long walletId, TransactionDTO transactionDTO) {
        Wallet existWalletSender = this.walletRepository.getById(walletId);
        Wallet existWalletRecipient = this.walletRepository.getById((long) transactionDTO.getWallet_id());

        this.transactionService.createTransferTransaction(existWalletSender, existWalletRecipient, transactionDTO);

        existWalletSender.setBalance(existWalletSender.getBalance() - transactionDTO.getAmount());
        existWalletRecipient.setBalance(existWalletRecipient.getBalance() + transactionDTO.getAmount());

        this.walletRepository.save(existWalletRecipient);
        this.walletRepository.save(existWalletSender);
    }
}
