package com.michael.walletapi.service.Impl;

import com.michael.walletapi.model.Transaction;
import com.michael.walletapi.model.User;
import com.michael.walletapi.model.Wallet;
import com.michael.walletapi.model.dto.TransactionDTO;
import com.michael.walletapi.model.dto.WalletDTO;
import com.michael.walletapi.repository.WalletRepository;
import org.jeasy.random.EasyRandom;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class WalletServiceImplTest {
    EasyRandom easyRandom = new EasyRandom();
    private Long id;

    @InjectMocks
    @Spy
    WalletServiceImpl walletService;

    @Mock
    WalletRepository walletRepository;

    @Mock
    TransactionServiceImpl transactionService;

    @Mock
    TransactionTypeServiceImpl typeService;

    @Before
    public void setUp(){
        this.id = easyRandom.nextLong();
    }

    @Test
    public void saveWallet_willReturnWallet(){
        // Given
        Wallet wallet = easyRandom.nextObject(Wallet.class);

        when(this.walletRepository.save(wallet)).thenReturn(wallet);

        // When
        var result = this.walletService.saveWallet(wallet);

        // Then
        assertEquals(wallet, result);
    }

    @Test
    public void getWalletById_willReturnWallet(){
        //Given
        Wallet wallet = easyRandom.nextObject(Wallet.class);

        when(this.walletRepository.findById(id)).thenReturn(Optional.of(wallet));

        //When
        var result = this.walletService.getWalletById(id);

        //Then
        assertEquals(wallet, result);
    }

    @Test
    public void createWallet_willReturnWallet(){
        // Given
        User user = easyRandom.nextObject(User.class);

        // When
        this.walletService.createWallet(user);

        // Then
        verify(this.walletRepository, times(1)).save(any());
    }

    @Test
    public void deleteProduct_willSuccess(){
        // Given
        doNothing().when(this.walletRepository).deleteById(id);

        // When
        this.walletService.deleteWalletById(id);

        // Then
        verify(this.walletRepository, times(1)).deleteById(id);
    }

    @Test
    public void addWallet_willSuccess(){
        // Given
        WalletDTO walletDTO = easyRandom.nextObject(WalletDTO.class);
        User user = easyRandom.nextObject(User.class);

        // When
        this.walletService.addWallet(user, walletDTO);

        // Then
        verify(this.walletRepository, times(1)).save(any());
    }

    @Test
    public void topUp_willSuccess(){
        // Given
        Wallet wallet = easyRandom.nextObject(Wallet.class);
        Transaction transaction = easyRandom.nextObject(Transaction.class);
        TransactionDTO transactionDTO = easyRandom.nextObject(TransactionDTO.class);

        doReturn(wallet).when(this.walletRepository).getById(id);
        doReturn(transaction).when(this.transactionService).createTopupTransaction(wallet, transactionDTO);


        // When
        var result = this.walletService.topUp(id, transactionDTO);

        // Then
        assertEquals(transaction, result);
    }

    @Test
    public void transfer_willSuccess(){
        // Given
        Wallet wallet = easyRandom.nextObject(Wallet.class);
        Transaction transaction = easyRandom.nextObject(Transaction.class);
        TransactionDTO transactionDTO = easyRandom.nextObject(TransactionDTO.class);

        doReturn(wallet).when(this.walletService).getWalletById(transactionDTO.getWallet_id());
        doReturn(wallet).when(this.walletRepository).getById(id);
        doReturn(wallet).when(this.walletRepository).getById(transactionDTO.getWallet_id());
        doReturn(transaction).when(this.transactionService).createTransferTransaction(wallet, wallet, transactionDTO);

        // When
        var result = this.walletService.transfer(id, transactionDTO);

        // Then
        assertEquals(transaction, result);

    }

    @Test
    public void updateUserWallet_willSuccess(){
        // Given
        Wallet wallet = easyRandom.nextObject(Wallet.class);
        WalletDTO walletDTO = easyRandom.nextObject(WalletDTO.class);

        when(this.walletRepository.findById(id)).thenReturn(Optional.of(wallet));

        when(this.walletRepository.save(wallet)).thenReturn(wallet);

        // When
        var result = this.walletService.updateUserWallet(id, walletDTO);

        // Then
        assertEquals(wallet, result);
        verify(this.walletRepository, times(1)).save(any());
    }

    @Test
    public void getUsersWallet_willSuccess(){
        // Given
        Wallet wallet = easyRandom.nextObject(Wallet.class);
        User user = easyRandom.nextObject(User.class);

        when(this.walletRepository.findByIdAndUser(id, user)).thenReturn(wallet);

        // When
        var result = this.walletService.getUsersWallet(id, user);

        // Given
        assertEquals(wallet, result);
    }



}