package com.michael.walletapi.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransactionTypeDTO {
    private String name;
    private LocalDateTime created_at;
}
