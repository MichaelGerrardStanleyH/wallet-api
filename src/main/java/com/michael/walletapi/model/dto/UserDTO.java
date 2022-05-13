package com.michael.walletapi.model.dto;

import com.michael.walletapi.model.Wallet;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class UserDTO {
    private Long id;
    private String name;
    private LocalDate dob;
    private LocalDateTime created_at;
    private String address;
    private Wallet wallet;

    private String password;
}
