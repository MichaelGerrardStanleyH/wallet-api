package com.michael.walletapi.repository;

import com.michael.walletapi.model.Transaction;
import com.michael.walletapi.model.User;
import com.michael.walletapi.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Transaction findByIdAndWallet(Long id, Wallet wallet);
}
