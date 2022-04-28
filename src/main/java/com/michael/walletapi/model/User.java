package com.michael.walletapi.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Date dob;
    private LocalDateTime created_at;

    // adress_id

    @OneToMany(mappedBy = "user")
    private List<Wallet> wallet;

    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;
}