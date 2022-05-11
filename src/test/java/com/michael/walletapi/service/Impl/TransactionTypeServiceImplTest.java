package com.michael.walletapi.service.Impl;

import com.michael.walletapi.model.TransactionType;
import com.michael.walletapi.model.dto.TransactionTypeDTO;
import com.michael.walletapi.repository.TransactionTypeRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class TransactionTypeServiceImplTest {
    EasyRandom easyRandom = new EasyRandom();
    private Long id;

    @InjectMocks
    TransactionTypeServiceImpl typeService;

    @Mock
    TransactionTypeRepository typeRepository;

    @Spy
    private ModelMapper mapper = new ModelMapper();

    @Before
    public void setUp(){
        this.id = easyRandom.nextLong();
    }

    @Test
    public void getAllTransactionType(){
        // Given
        TransactionType transactionType1 = easyRandom.nextObject(TransactionType.class);
        TransactionType transactionType2 = easyRandom.nextObject(TransactionType.class);

        List<TransactionType> listType = new ArrayList<>();
        listType.add(transactionType1);
        listType.add(transactionType2);

        when(this.typeRepository.findAll()).thenReturn(listType);

        // When
        var result = this.typeService.getAllTransactionType();

        // Then
        assertEquals(listType, result);
    }

    @Test
    public void getTransactionTypeById(){
        // Given
        TransactionType transactionType = easyRandom.nextObject(TransactionType.class);

        when(this.typeRepository.findById(id)).thenReturn(Optional.of(transactionType));

        // When
        var result = this.typeService.getTransactionTypeById(id);

        // Then
        assertEquals(transactionType, result);
    }

    @Test
    public void createTransactionType(){
        // Given
        TransactionType transactionType = easyRandom.nextObject(TransactionType.class);
        TransactionTypeDTO typeDTO= easyRandom.nextObject(TransactionTypeDTO.class);

        when(this.typeRepository.save(any())).thenReturn(transactionType);

        // When
        var result = this.typeService.createTransactionType(typeDTO);

        // Then
        assertEquals(transactionType, result);
    }

    @Test
    public void updateTransactionType(){
        // Given
        TransactionType transactionType = easyRandom.nextObject(TransactionType.class);
        TransactionTypeDTO typeDTO= easyRandom.nextObject(TransactionTypeDTO.class);

        when(this.typeRepository.getById(id)).thenReturn(transactionType);
        when(this.typeRepository.save(transactionType)).thenReturn(transactionType);

        // When
        var result = this.typeService.updateTransactionType(id, typeDTO);

        // Then
        assertEquals(transactionType, result);
    }

    @Test
    public void deleteTransactionType(){
        // Given
        doNothing().when(this.typeRepository).deleteById(id);

        // When
        this.typeService.deleteTransactionType(id);

        // Then
        verify(this.typeRepository, times(1)).deleteById(id);
    }

}








