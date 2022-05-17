package com.michael.walletapi.controller;

import com.michael.walletapi.model.BaseResponse;
import com.michael.walletapi.model.Transaction;
import com.michael.walletapi.model.User;
import com.michael.walletapi.model.Wallet;
import com.michael.walletapi.model.dto.TokenResponse;
import com.michael.walletapi.model.dto.TransactionDTO;
import com.michael.walletapi.model.dto.UserDTO;
import com.michael.walletapi.model.dto.WalletDTO;
import com.michael.walletapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

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

    @PostMapping("/token")
    public ResponseEntity<BaseResponse<TokenResponse>> generateToken(@RequestBody UserDTO userDTO){
        BaseResponse<TokenResponse> baseResponse = new BaseResponse<>();

        TokenResponse tokenResponse = this.userService.generateToken(userDTO);

        baseResponse.setData(tokenResponse);

        if(tokenResponse == null ){
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

    @PostMapping("/{id}/wallets")
    public ResponseEntity<BaseResponse<Wallet>> addWallet(@PathVariable Long id, @RequestBody WalletDTO walletDTO){
        BaseResponse<Wallet> baseResponse = new BaseResponse<>();

        Wallet wallet = this.userService.addWallet(id, walletDTO);


        baseResponse.setMessage("Success");
        baseResponse.setSuccess(true);
        baseResponse.setData(wallet);
        return ResponseEntity.status(HttpStatus.CREATED).body(baseResponse);
    }

    @PostMapping("/{userId}/topup/{walletId}")
    public ResponseEntity<BaseResponse<Transaction>> topUp(@PathVariable("userId") Long userId, @PathVariable("walletId") Long walletId, @RequestBody TransactionDTO transactionDTO){
        BaseResponse<Transaction> baseResponse = new BaseResponse<>();

        Transaction transaction = this.userService.topUp(userId, walletId, transactionDTO);

        baseResponse.setData(transaction);

        if(transaction == null ){
            baseResponse.setMessage("Failed");
            baseResponse.setSuccess(false);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(baseResponse);
        }else{
            baseResponse.setMessage("Success");
            baseResponse.setSuccess(true);
            return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
        }
    }

    @PostMapping("/{userId}/transfer/{walletId}")
    public ResponseEntity<BaseResponse<Transaction>> transfer(@PathVariable("userId") Long userId, @PathVariable("walletId") Long walletId, @RequestBody TransactionDTO transactionDTO){
        BaseResponse<Transaction> baseResponse = new BaseResponse<>();

        Transaction transfer = this.userService.transfer(userId, walletId, transactionDTO);

        baseResponse.setData(transfer);

        if(transfer == null ){
            baseResponse.setMessage("Failed");
            baseResponse.setSuccess(false);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(baseResponse);
        }else{
            baseResponse.setMessage("Success");
            baseResponse.setSuccess(true);
            return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
        }
    }

    @GetMapping("/{userId}/wallets/{walletId}")
    public ResponseEntity<BaseResponse<Wallet>> getUsersWalletById(@PathVariable("userId") Long userId, @PathVariable("walletId") Long walletId){
        BaseResponse<Wallet> baseResponse = new BaseResponse<>();

        Wallet wallet = this.userService.getUsersWalletById(userId, walletId);

        baseResponse.setData(wallet);

        if(wallet == null ){
            baseResponse.setMessage("Failed");
            baseResponse.setSuccess(false);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(baseResponse);
        }else{
            baseResponse.setMessage("Success");
            baseResponse.setSuccess(true);
            return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
        }
    }

    @PutMapping("/{userId}/wallets/{walletId}")
    public ResponseEntity<BaseResponse<Wallet>> updateWallet(@PathVariable("userId") Long userId, @PathVariable("walletId") Long walletId, @RequestBody WalletDTO walletDTO){
        BaseResponse<Wallet> baseResponse = new BaseResponse<>();

        Wallet wallet = this.userService.updateUsersWallet(userId, walletId, walletDTO);

        baseResponse.setData(wallet);

        if(wallet == null ){
            baseResponse.setMessage("Failed");
            baseResponse.setSuccess(false);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(baseResponse);
        }else{
            baseResponse.setMessage("Success");
            baseResponse.setSuccess(true);
            return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
        }
    }

    @DeleteMapping("/{userId}/wallets/{walletId}")
    public ResponseEntity<Void> deleteUsersWallet(@PathVariable("userId") Long userId, @PathVariable("walletId") Long walletId){
        this.userService.deleteUsersWallet(userId, walletId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{userId}/wallets/{walletId}/transactions/{transactionId}")
    public ResponseEntity<BaseResponse<Object>> getUsersTransaction(@PathVariable("userId") Long userId, @PathVariable("walletId") Long walletId, @PathVariable("transactionId") Long transactionId){
        Transaction usersTransaction = this.userService.getUsersTransaction(userId, walletId, transactionId);

        BaseResponse<Object> baseResponse = BaseResponse.builder()
                .message("Success")
                .success(true)
                .data(usersTransaction)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(baseResponse);
    }

}