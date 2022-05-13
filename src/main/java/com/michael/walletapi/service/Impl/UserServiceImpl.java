package com.michael.walletapi.service.Impl;

import com.michael.walletapi.model.*;
import com.michael.walletapi.model.dto.TransactionDTO;
import com.michael.walletapi.model.dto.UserDTO;
import com.michael.walletapi.model.dto.WalletDTO;
import com.michael.walletapi.repository.AddressRepository;
import com.michael.walletapi.repository.UserRepository;
import com.michael.walletapi.service.TransactionService;
import com.michael.walletapi.service.UserService;
import com.michael.walletapi.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    WalletService walletService;

    @Autowired
    TransactionService transactionService;

    public LocalDateTime getTimeNow(){
        return LocalDateTime.now();
    }

    public List<User> getAllUser() {
        List<User> listUser = this.userRepository.findAll();

        if(listUser.isEmpty()){
            return null;
        }else{
            return listUser;
        }
    }

    public User getUserById(Long id){
        Optional<User> optionalUser = this.userRepository.findById(id);

        if(optionalUser.isEmpty()){
            return null;
        }else{
            return optionalUser.get();
        }
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

        Wallet newWallet = this.walletService.createWallet(savedUser);

        savedUser.setWallet(List.of(newWallet));

        if(savedUser.getId() == null){
            return null;
        }else{
            return savedUser;
        }

    }

    public User updateUser(Long id, UserDTO userDTO) {
        Optional<User> optionalUser = this.userRepository.findById(id);

        User existUser;

        if (optionalUser.isEmpty()) {
            return null;
        } else {
            existUser = optionalUser.get();
        }

        existUser.setName(userDTO.getName());
        existUser.setDob(userDTO.getDob());

        return this.userRepository.save(existUser);
    }


    public void deleteUser(Long id) {
        this.userRepository.deleteById(id);
    }

    public Wallet addWallet(Long id, WalletDTO walletDTO) {
        User existUser = this.userRepository.getById(id);

        Wallet newWallet = this.walletService.addWallet(existUser, walletDTO);

        return newWallet;
    }

    public Transaction topUp(Long userId, Long walletId, TransactionDTO transactionDTO) {
        // exception

        User user = this.getUserById(userId);

        if(user == null){
            return null;
        }

        if(this.walletService.getUsersWallet(walletId, user) == null){
            return null;
        }

        return this.walletService.topUp(walletId, transactionDTO);
    }

    public Transaction transfer(Long userId, Long walletId, TransactionDTO transactionDTO) {
        // exception
        User user = this.getUserById(userId);

        if(user == null){
            return null;
        }

        if(this.walletService.getUsersWallet(walletId, user) == null){
            return null;
        }

        return this.walletService.transfer(walletId, transactionDTO);
    }

    public Wallet updateUsersWallet(Long userId, Long walletId, WalletDTO walletDTO) {
        Wallet existWallet = this.getUsersWalletById(userId, walletId);

        if (existWallet != null) {
            return this.walletService.updateUserWallet(walletId, walletDTO);
        }else{
            return null;
        }
    }

    public Wallet getUsersWalletById(Long userId, Long walletId) {
        User user = this.getUserById(userId);

        return this.walletService.getUsersWallet(walletId, user);
    }

    public ResponseEntity<Void> deleteUsersWallet(Long userId, Long walletId){
        if(this.getUsersWalletById(userId, walletId) == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }else {
            this.walletService.deleteWalletById(walletId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @Override
    public Transaction getUsersTransaction(Long userId, Long walletId, Long transactionId) {
        User existUser = this.getUserById(userId);
        Wallet existWallet = this.walletService.getUsersWallet(walletId, existUser);
        return this.transactionService.getUsersTransactionById(transactionId, existWallet);
    }
}

