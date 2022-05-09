package com.michael.walletapi.service.Impl;

import com.michael.walletapi.model.Address;
import com.michael.walletapi.model.User;
import com.michael.walletapi.model.Wallet;
import com.michael.walletapi.model.dto.UserDTO;
import com.michael.walletapi.repository.AddressRepository;
import com.michael.walletapi.repository.UserRepository;
import org.jeasy.random.EasyRandom;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class UserServiceImplTest {
    EasyRandom easyRandom = new EasyRandom();
    private Long id;

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Mock
    WalletServiceImpl walletService;

    @Mock
    AddressRepository addressRepository;

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


        when(this.addressRepository.save(address)).thenReturn(address);
        when(this.userRepository.save(any())).thenReturn(user);
        when(this.walletService.createWallet(user)).thenReturn(wallet);

        // When
        var result = this.userService.createUser(userDTO);

        // Then
        verify(this.userRepository, times(1)).save(any());
        assertEquals(user, result);
    }


}