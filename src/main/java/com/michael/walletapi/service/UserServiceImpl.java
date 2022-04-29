package com.michael.walletapi.service;

import com.michael.walletapi.model.Address;
import com.michael.walletapi.model.User;
import com.michael.walletapi.model.Wallet;
import com.michael.walletapi.model.dto.UserDTO;
import com.michael.walletapi.model.dto.WalletDTO;
import com.michael.walletapi.repository.AddressRepository;
import com.michael.walletapi.repository.UserRepository;
import com.michael.walletapi.repository.WalletRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl {
    @Autowired
    UserRepository userRepository;

    @Autowired
    WalletRepository walletRepository;

    @Autowired
    AddressRepository addressRepository;

    public LocalDateTime getTimeNow(){
        return LocalDateTime.now();
    }



    public List<User> getAllUser() {
        return this.userRepository.findAll();
    }

    public User getUserById(Long id) {
        return this.userRepository.findById(id).get();
    }

    public User createUser(UserDTO userDTO) {
        Address newAddress = Address.builder()
                .address(userDTO.getAddress())
                .created_at(getTimeNow())
                .build();

        Address savedAddress = this.addressRepository.save(newAddress);

        User newUser = User.builder().name(userDTO.getName())
                .dob(userDTO.getDob())
                .created_at(getTimeNow())
                .address(savedAddress)
                .build();

        User savedUser = this.userRepository.save(newUser);

        Wallet newWallet = Wallet.builder()
                .name("Kantong Utama")
                .balance(0)
                .created_at(getTimeNow())
                .user(newUser)
                .build();

        Wallet savedWallet = this.walletRepository.save(newWallet);

        savedUser.setWallet(List.of(savedWallet));

        return savedUser;
    }

    public User updateUser(Long id, UserDTO userDTO) {
        User existUser = this.getUserById(id);

        existUser.setName(userDTO.getName());
        existUser.setDob(userDTO.getDob());

        return this.userRepository.save(existUser);
    }


    public void deleteUser(Long id) {
        this.userRepository.deleteById(id);
    }

    public User addWallet(Long id, WalletDTO walletDTO) {
        User existUser = this.userRepository.findById(id).get();

        Wallet newWallet = Wallet.builder()
                .name(walletDTO.getName())
                .user(existUser)
                .created_at(getTimeNow())
                .build();

        Wallet savedWallet = this.walletRepository.save(newWallet);

        existUser.addWallet(savedWallet);

        return this.userRepository.save(existUser);
    }
}
