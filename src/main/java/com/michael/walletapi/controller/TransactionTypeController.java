package com.michael.walletapi.controller;

import com.michael.walletapi.model.TransactionType;
import com.michael.walletapi.model.dto.TransactionTypeDTO;
import com.michael.walletapi.service.TransactionTypeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/type")
public class TransactionTypeController {
    @Autowired
    TransactionTypeServiceImpl transactionTypeServiceImpl;

    @GetMapping()
    public List<TransactionType> getAllTransactionType(){
        return this.transactionTypeServiceImpl.getAllTransactionType();
    }

    @PostMapping()
    public TransactionType createTransactionType(@RequestBody TransactionTypeDTO transactionTypeDTO){
        return this.transactionTypeServiceImpl.createTransactionType(transactionTypeDTO);
    }

    @PutMapping("/{id}")
    public TransactionType updateTransactionType(@PathVariable Long id, @RequestBody TransactionTypeDTO transactionTypeDTO){
        return this.transactionTypeServiceImpl.updateTransactionType(id, transactionTypeDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteTransactionType(@PathVariable Long id){
        this.transactionTypeServiceImpl.deleteTransactionType(id);
    }
}
