package com.michael.walletapi.controller;

import com.michael.walletapi.exception.InsufficientBalanceException;
import com.michael.walletapi.model.BaseResponse;
import com.michael.walletapi.model.User;
import com.michael.walletapi.model.dto.TransactionDTO;
import com.michael.walletapi.model.dto.UserDTO;
import com.michael.walletapi.model.dto.WalletDTO;
import com.michael.walletapi.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserServiceImpl userService;

    @GetMapping()
    public ResponseEntity<BaseResponse<List<User>>> getAllUser(){
        BaseResponse<List<User>> baseResponse = new BaseResponse<>();

        List<User> listUser  = this.userService.getAllUser();

        baseResponse.setData(listUser);

        if(listUser == null){
            baseResponse.setMessage("Failed");
            baseResponse.setSuccess(false);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(baseResponse);
        }else{
            baseResponse.setMessage("Success");
            baseResponse.setSuccess(true);
            return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<User>> getUserById(@PathVariable Long id){
        BaseResponse<User> baseResponse = new BaseResponse<>();

        User user = this.userService.getUserById(id);

        baseResponse.setData(user);

        if(user == null ){
            baseResponse.setMessage("Failed");
            baseResponse.setSuccess(false);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(baseResponse);
        }else{
            baseResponse.setMessage("Success");
            baseResponse.setSuccess(true);
            return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
        }
    }

    @PostMapping()
    public ResponseEntity<BaseResponse<User>> createUser(@RequestBody UserDTO userDTO){
        BaseResponse<User> baseResponse = new BaseResponse<>();

        User user = this.userService.createUser(userDTO);

        baseResponse.setData(user);

        if(user == null ){
            baseResponse.setMessage("Failed");
            baseResponse.setSuccess(false);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(baseResponse);
        }else{
            baseResponse.setMessage("Success");
            baseResponse.setSuccess(true);
            return ResponseEntity.status(HttpStatus.CREATED).body(baseResponse);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<User>> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO){
        BaseResponse<User> baseResponse = new BaseResponse<>();

        User user = this.userService.updateUser(id, userDTO);

        baseResponse.setData(user);

        if(user == null ){
            baseResponse.setMessage("Failed");
            baseResponse.setSuccess(false);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(baseResponse);
        }else{
            baseResponse.setMessage("Success");
            baseResponse.setSuccess(true);
            return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>  deleteUser(@PathVariable Long id){
        this.userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/{id}/wallet")
    public User addWallet(@PathVariable Long id, @RequestBody WalletDTO walletDTO){
        return this.userService.addWallet(id, walletDTO);
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