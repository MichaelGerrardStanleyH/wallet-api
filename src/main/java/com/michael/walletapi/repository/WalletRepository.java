package com.michael.walletapi.repository;

import com.michael.walletapi.model.User;
import com.michael.walletapi.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Wallet findByIdAndUser(Long id, User user);
}
