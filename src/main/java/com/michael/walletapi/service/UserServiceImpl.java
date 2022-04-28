package com.michael.walletapi.service;

import com.michael.walletapi.model.Address;
import com.michael.walletapi.model.User;
import com.michael.walletapi.model.Wallet;
import com.michael.walletapi.model.dto.UserDTO;
import com.michael.walletapi.repository.UserRepository;
import com.michael.walletapi.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserServiceImpl {
    @Autowired
    UserRepository userRepository;

    @Autowired
    WalletRepository walletRepository;

    LocalDateTime localDateTime = LocalDateTime.now();

    public List<User> getAllUser() {
        return this.userRepository.findAll();
    }

    public User createUser(UserDTO userDTO) {


        User newUser = User.builder().name(userDTO.getName())
                .dob(userDTO.getDob())
                .created_at(localDateTime)
                .build();

        User savedUser = this.userRepository.save(newUser);

        Wallet newWallet = Wallet.builder()
                .name("Kantong Utama")
                .balance(0)
                .created_at(localDateTime)
                .user(savedUser)
                .build();

        this.walletRepository.save(newWallet);

        return savedUser;
    }

    public void deleteUser(Long id) {
        this.userRepository.deleteById(id);
    }
}
