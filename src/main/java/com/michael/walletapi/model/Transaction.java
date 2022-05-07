package com.michael.walletapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private double amount;
    private LocalDateTime created_at;

    private Long fromWallet;
    private Long toWallet;

    @OneToOne
    @JoinColumn(name = "transaction_type_id")
    private TransactionType transactionType;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;
}
