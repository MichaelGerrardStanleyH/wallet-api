package com.michael.walletapi.model.dto;

import com.michael.walletapi.model.TransactionType;
import com.michael.walletapi.model.Wallet;
import lombok.Data;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

@Data
public class TransactionDTO {
    private String description;
    private double amount;
    private LocalDateTime created_at;
    private Long transaction_type_id;
    private Long wallet_id;
}
