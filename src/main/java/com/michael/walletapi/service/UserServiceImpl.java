package com.michael.walletapi.service;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.michael.walletapi.exception.InsufficientBalanceException;
import com.michael.walletapi.model.Address;
import com.michael.walletapi.model.BaseResponse;
import com.michael.walletapi.model.User;
import com.michael.walletapi.model.Wallet;
import com.michael.walletapi.model.dto.TransactionDTO;
import com.michael.walletapi.model.dto.UserDTO;
import com.michael.walletapi.model.dto.WalletDTO;
import com.michael.walletapi.repository.AddressRepository;
import com.michael.walletapi.repository.UserRepository;
import com.michael.walletapi.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl {
    @Autowired
    UserRepository userRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    WalletServiceImpl walletService;

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

    public User addWallet(Long id, WalletDTO walletDTO) {
        User existUser = this.userRepository.findById(id).get();

        Wallet newWallet = this.walletService.addWallet(existUser, walletDTO);

        existUser.addWallet(newWallet);

        return existUser;
    }

    public void topUp(Long userId, Long walletId, TransactionDTO transactionDTO) {
        // exception

        this.walletService.topUp(walletId, transactionDTO);
    }

    public void transfer(Long userId, Long walletId, TransactionDTO transactionDTO) {
        // exception

        this.walletService.transfer(walletId, transactionDTO);
    }
}
