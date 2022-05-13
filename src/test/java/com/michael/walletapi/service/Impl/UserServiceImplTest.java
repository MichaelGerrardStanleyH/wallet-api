package com.michael.walletapi.service.Impl;

import com.michael.walletapi.model.Address;
import com.michael.walletapi.model.Transaction;
import com.michael.walletapi.model.User;
import com.michael.walletapi.model.Wallet;
import com.michael.walletapi.model.dto.TransactionDTO;
import com.michael.walletapi.model.dto.UserDTO;
import com.michael.walletapi.model.dto.WalletDTO;
import com.michael.walletapi.repository.AddressRepository;
import com.michael.walletapi.repository.UserRepository;
import org.jeasy.random.EasyRandom;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {
    EasyRandom easyRandom = new EasyRandom();
    private Long id;

    @InjectMocks
    @Spy
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Mock
    WalletServiceImpl walletService;

    @Mock
    AddressRepository addressRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Spy
    private ModelMapper mapper = new ModelMapper();

    @Before
    public void setUp(){
        this.id = easyRandom.nextLong();
    }

    @Test
    public void getAllUser_willSuccess(){
        //Given
        User user1 = easyRandom.nextObject(User.class);
        User user2 = easyRandom.nextObject(User.class);

        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);

        when(this.userRepository.findAll()).thenReturn(userList);

        // When
        var result = this.userService.getAllUser();

        // Then
        assertEquals(userList, result);
    }

    @Test
    public void getUserById_willSuccess(){
        // Given
        User user = easyRandom.nextObject(User.class);

        when(this.userRepository.findById(id)).thenReturn(Optional.of(user));

        // When
        var result = this.userService.getUserById(id);

        // Then
        assertEquals(user, result);
    }

    @Test
    public void createUser_willSuccess(){
        // Given
        User user = easyRandom.nextObject(User.class);
        UserDTO userDTO = this.mapper.map(user, UserDTO.class);
        Address address = easyRandom.nextObject(Address.class);
        Wallet wallet = easyRandom.nextObject(Wallet.class);


        when(this.userRepository.save(any())).thenReturn(user);
        when(this.walletService.createWallet(user)).thenReturn(wallet);

        // When
        var result = this.userService.createUser(userDTO);

        // Then
        verify(this.userRepository, times(1)).save(any());
        assertEquals(user, result);
    }

    @Test
    public void updateUser_willSuccess(){
        // Given
        User user = easyRandom.nextObject(User.class);
        UserDTO userDTO = easyRandom.nextObject(UserDTO.class);

        when(this.userRepository.findById(id)).thenReturn(Optional.of(user));
        when(this.userRepository.save(user)).thenReturn(user);
        
        // When
        var result = this.userService.updateUser(id, userDTO);

        // Then
        assertEquals(user, result);
    }

    @Test
    public void deleteUser_willSuccess(){
        // Given
        doNothing().when(this.userRepository).deleteById(id);

        // When
        this.userService.deleteUser(id);

        // Then
        verify(this.userRepository, times(1)).deleteById(id);
    }

    @Test
    public void addWallet_willSuccess(){
        // Given
        User user = easyRandom.nextObject(User.class);
        WalletDTO walletDTO = easyRandom.nextObject(WalletDTO.class);
        Wallet wallet = easyRandom.nextObject(Wallet.class);

        when(this.userRepository.getById(id)).thenReturn(user);
        when(this.walletService.addWallet(user, walletDTO)).thenReturn(wallet);

        // When
        var result = this.userService.addWallet(id, walletDTO);

        // Then
        assertEquals(wallet, result);
    }

    @Test
    public void topUp_willSuccess(){
        // Given
        User user = easyRandom.nextObject(User.class);
        Wallet wallet = easyRandom.nextObject(Wallet.class);
        Transaction transaction = easyRandom.nextObject(Transaction.class);
        TransactionDTO transactionDTO = easyRandom.nextObject(TransactionDTO.class);

        doReturn(user).when(this.userService).getUserById(id);
        doReturn(wallet).when(this.walletService).getUsersWallet(id, user);
        doReturn(transaction).when(this.walletService).topUp(id, transactionDTO);

        // When
        var result = this.userService.topUp(id, id, transactionDTO);

        // Then
        assertEquals(transaction, result);

    }

    @Test
    public void transfer_willSuccess(){
        // Given
        User user = easyRandom.nextObject(User.class);
        Wallet wallet = easyRandom.nextObject(Wallet.class);
        Transaction transaction = easyRandom.nextObject(Transaction.class);
        TransactionDTO transactionDTO = easyRandom.nextObject(TransactionDTO.class);

        doReturn(user).when(this.userService).getUserById(id);
        doReturn(wallet).when(this.walletService).getUsersWallet(id, user);
        doReturn(transaction).when(this.walletService).transfer(id, transactionDTO);

        // When
        var result = this.userService.transfer(id, id, transactionDTO);

        // Then
        assertEquals(transaction, result);
    }

    @Test
    public void updateUsersWallet_willSuccess(){
        // Given
        Wallet wallet = easyRandom.nextObject(Wallet.class);
        WalletDTO walletDTO = easyRandom.nextObject(WalletDTO.class);

        when(this.userService.getUsersWalletById(id, wallet.getId())).thenReturn(wallet);
        when(this.walletService.updateUserWallet(wallet.getId(), walletDTO)).thenReturn(wallet);
        // When
        var result = this.userService.updateUsersWallet(id, wallet.getId(), walletDTO);

        // Then
        assertEquals(wallet, result);
    }

    @Test
    public void getUsersWalleById_willSuccess(){
        // Given
        User user = easyRandom.nextObject(User.class);
        Wallet wallet = easyRandom.nextObject(Wallet.class);

        doReturn(user).when(this.userService).getUserById(id);
        doReturn(wallet).when(this.walletService).getUsersWallet(id, user);

        // When
        var result = this.userService.getUsersWalletById(id, id);

        // Then
        assertEquals(wallet, result);
    }


    @Test
    public void deletUsersWallet_willSuccess(){
        // Given
        Wallet wallet = easyRandom.nextObject(Wallet.class);

        doReturn(wallet).when(this.userService).getUsersWalletById(id, id);

        // When
        var result = this.userService.deleteUsersWallet(id, id);

        // Then
        assertEquals(ResponseEntity.status(HttpStatus.NO_CONTENT).build(), result);
    }

}