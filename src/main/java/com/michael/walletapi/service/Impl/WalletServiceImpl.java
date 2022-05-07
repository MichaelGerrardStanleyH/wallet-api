package com.michael.walletapi.service.Impl;

import com.michael.walletapi.model.Transaction;
import com.michael.walletapi.model.User;
import com.michael.walletapi.model.Wallet;
import com.michael.walletapi.model.dto.TransactionDTO;
import com.michael.walletapi.model.dto.WalletDTO;
import com.michael.walletapi.repository.WalletRepository;
import com.michael.walletapi.service.Impl.TransactionServiceImpl;
import com.michael.walletapi.service.TransactionService;
import com.michael.walletapi.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class WalletServiceImpl implements WalletService {
    @Autowired
    WalletRepository walletRepository;

    @Autowired
    TransactionService transactionService;

    public LocalDateTime getTimeNow(){
        return LocalDateTime.now();
    }

    public Wallet saveWallet(Wallet wallet){
        return this.walletRepository.save(wallet);
    }

    public Wallet getWalletById(Long id){
        Optional<Wallet> optionalWallet = this.walletRepository.findById(id);

        if(optionalWallet.isEmpty()){
            return null;
        }else{
            return optionalWallet.get();
        }
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
    public void deleteWalletById(Long id){
        this.walletRepository.deleteById(id);
    }

    public Wallet addWallet(User existUser, WalletDTO walletDTO) {
        Wallet newWallet = Wallet.builder()
                .name(walletDTO.getName())
                .user(existUser)
                .created_at(getTimeNow())
                .build();

        return this.walletRepository.save(newWallet);
    }

    public Transaction topUp(Long walletId, TransactionDTO transactionDTO) {
        Wallet existWallet = this.walletRepository.getById(walletId);

        Transaction topUpTransaction = this.transactionService.createTopupTransaction(existWallet, transactionDTO);

        if(topUpTransaction == null){
            return null;
        }

        existWallet.setBalance(existWallet.getBalance() + transactionDTO.getAmount());

        this.walletRepository.save(existWallet);

        return topUpTransaction;
    }

    public Transaction transfer(Long walletId, TransactionDTO transactionDTO) {
        if(this.getWalletById((long) transactionDTO.getWallet_id()) == null){
            return null;
        }

        Wallet existWalletSender = this.walletRepository.getById(walletId);
        Wallet existWalletRecipient = this.walletRepository.getById((long) transactionDTO.getWallet_id());

        if(existWalletSender.getBalance() < transactionDTO.getAmount()){
            return null;
        }

        Transaction transactionSender = this.transactionService.createTransferTransaction(existWalletSender, existWalletRecipient, transactionDTO);

        if(transactionSender == null){
            return null;
        }

        existWalletSender.setBalance(existWalletSender.getBalance() - transactionDTO.getAmount());
        existWalletRecipient.setBalance(existWalletRecipient.getBalance() + transactionDTO.getAmount());

        this.walletRepository.save(existWalletRecipient);
        this.walletRepository.save(existWalletSender);

        return transactionSender;
    }

    public Wallet updateUserWallet(Long walletId, WalletDTO walletDTO) {
        Wallet existWallet = this.getWalletById(walletId);

        existWallet.setName(walletDTO.getName());

        return this.walletRepository.save(existWallet);
    }

    public Wallet getUsersWallet(Long walletId, User existUser) {
        return this.walletRepository.findByIdAndUser(walletId, existUser);
    }
}
