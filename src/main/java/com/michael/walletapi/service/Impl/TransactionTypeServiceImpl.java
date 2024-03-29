package com.michael.walletapi.service.Impl;

import com.michael.walletapi.model.TransactionType;
import com.michael.walletapi.model.dto.TransactionTypeDTO;
import com.michael.walletapi.repository.TransactionTypeRepository;
import com.michael.walletapi.service.TransactionTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionTypeServiceImpl implements TransactionTypeService {
    @Autowired
    TransactionTypeRepository transactionTypeRepository;

    public LocalDateTime getTimeNow(){
        return LocalDateTime.now();
    }

    public List<TransactionType> getAllTransactionType() {
        return this.transactionTypeRepository.findAll();
    }

    public TransactionType getTransactionTypeById(Long id){
        Optional<TransactionType> optionalType = this.transactionTypeRepository.findById(id);

        if(optionalType.isEmpty()){
            return null;
        }else{
            return optionalType.get();
        }
    }

    public TransactionType createTransactionType(TransactionTypeDTO transactionTypeDTO) {
        TransactionType newType = TransactionType.builder()
                .name(transactionTypeDTO.getName())
                .created_at(getTimeNow())
                .build();

        return this.transactionTypeRepository.save(newType);
    }

    public TransactionType updateTransactionType(Long id, TransactionTypeDTO transactionTypeDTO) {
        TransactionType existType = this.transactionTypeRepository.getById(id);

        existType.setName(transactionTypeDTO.getName());

        return this.transactionTypeRepository.save(existType);
    }

    public void deleteTransactionType(Long id) {
        this.transactionTypeRepository.deleteById(id);
    }
}
