package com.michael.walletapi.service;

import com.michael.walletapi.model.Transaction;
import com.michael.walletapi.model.User;
import com.michael.walletapi.model.Wallet;
import com.michael.walletapi.model.dto.TransactionDTO;
import com.michael.walletapi.model.dto.UserDTO;
import com.michael.walletapi.model.dto.WalletDTO;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface UserService {
    public LocalDateTime getTimeNow();

    public List<User> getAllUser();

    public User getUserById(Long id);

    public User createUser(UserDTO userDTO);

    public User updateUser(Long id, UserDTO userDTO);

    public void deleteUser(Long id);

    public User addWallet(Long id, WalletDTO walletDTO);

    public Transaction topUp(Long userId, Long walletId, TransactionDTO transactionDTO);

    public Transaction transfer(Long userId, Long walletId, TransactionDTO transactionDTO);

    public Wallet updateUsersWallet(Long userId, Long walletId, WalletDTO walletDTO);

    public Wallet getUsersWalletById(Long userId, Long walletId);

    public ResponseEntity<Void> deleteUsersWallet(Long userId, Long walletId);
}
