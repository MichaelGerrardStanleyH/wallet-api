package com.michael.walletapi.controller;

import com.michael.walletapi.exception.InsufficientBalanceException;
import com.michael.walletapi.model.User;
import com.michael.walletapi.model.dto.TransactionDTO;
import com.michael.walletapi.model.dto.UserDTO;
import com.michael.walletapi.model.dto.WalletDTO;
import com.michael.walletapi.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserServiceImpl userService;

    @GetMapping()
    public List<User> getAllUser(){
        return this.userService.getAllUser();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) throws InsufficientBalanceException {
        return this.userService.getUserById(id);
    }

    @PostMapping()
    public User createUser(@RequestBody UserDTO userDTO){
        return this.userService.createUser(userDTO);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO){
        return this.userService.updateUser(id, userDTO);
    }

    @PostMapping("/{id}/wallet")
    public User addWallet(@PathVariable Long id, @RequestBody WalletDTO walletDTO){
        return this.userService.addWallet(id, walletDTO);
    }

    @DeleteMapping("/{id}")
    public void  deleteUser(@PathVariable Long id){
         this.userService.deleteUser(id);
    }

    @PostMapping("/{userId}/topup/{walletId}")
    public void topup(@PathVariable("userId") Long userId, @PathVariable("walletId") Long walletId, @RequestBody TransactionDTO transactionDTO){
        this.userService.topUp(userId, walletId, transactionDTO);
    }

    @PostMapping("/{userId}/transfer/{walletId}")
    public void transfer(@PathVariable("userId") Long userId, @PathVariable("walletId") Long walletId, @RequestBody TransactionDTO transactionDTO){
        this.userService.transfer(userId, walletId, transactionDTO);
    }

}