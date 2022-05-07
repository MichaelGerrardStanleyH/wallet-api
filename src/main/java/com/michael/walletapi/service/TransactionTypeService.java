package com.michael.walletapi.service;

import com.michael.walletapi.model.TransactionType;
import com.michael.walletapi.model.dto.TransactionTypeDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionTypeService {
    public LocalDateTime getTimeNow();

    public List<TransactionType> getAllTransactionType();

    public TransactionType getTransactionTypeById(Long id);

    public TransactionType createTransactionType(TransactionTypeDTO transactionTypeDTO);

    public TransactionType updateTransactionType(Long id, TransactionTypeDTO transactionTypeDTO);

    public void deleteTransactionType(Long id);
}
