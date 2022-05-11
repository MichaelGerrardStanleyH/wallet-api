package com.michael.walletapi.service.Impl;

import com.michael.walletapi.model.Transaction;
import com.michael.walletapi.model.TransactionType;
import com.michael.walletapi.model.Wallet;
import com.michael.walletapi.model.dto.TransactionDTO;
import com.michael.walletapi.repository.TransactionRepository;
import org.jeasy.random.EasyRandom;
import org.junit.Before;
import org.junit.Test;
import org.junit.platform.commons.annotation.Testable;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

@RunWith(SpringRunner.class)
public class TransactionServiceImplTest {
    EasyRandom easyRandom = new EasyRandom();
    private Long id;

    @InjectMocks
    TransactionServiceImpl transactionService;

    @Mock
    TransactionRepository transactionRepository;

    @Mock
    TransactionTypeServiceImpl typeService;

    @Before
    public void setUp(){
        this.id = easyRandom.nextLong();
    }

    @Test
    public void createTopupTransaction_willSuccess(){
        // Given
        Wallet wallet = easyRandom.nextObject(Wallet.class);
        TransactionDTO transactionDTO = easyRandom.nextObject(TransactionDTO.class);
        TransactionType transactionType = easyRandom.nextObject(TransactionType.class);
        Transaction transaction = easyRandom.nextObject(Transaction.class);

        doReturn(transactionType).when(this.typeService).getTransactionTypeById(transactionDTO.getTransaction_type_id());
        doReturn(transaction).when(this.transactionRepository).save(any());

        // When
        var result = this.transactionService.createTopupTransaction(wallet, transactionDTO);

        // Then
        assertEquals(transaction, result);
    }

    @Test
    public void createTransfer_willSuccess(){
        // Given
        Wallet wallet = easyRandom.nextObject(Wallet.class);
        TransactionDTO transactionDTO = easyRandom.nextObject(TransactionDTO.class);
        TransactionType transactionType = easyRandom.nextObject(TransactionType.class);
        Transaction transaction = easyRandom.nextObject(Transaction.class);

        doReturn(transactionType).when(this.typeService).getTransactionTypeById(transactionDTO.getTransaction_type_id());
        doReturn(transaction).when(this.transactionRepository).save(any());

        // When
        var result = this.transactionService.createTransferTransaction(wallet, wallet, transactionDTO);

        // Then
        assertEquals(transaction, result);
    }

}