package com.example.netbankingapplication.data.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "phone_no")
    private String phoneNumber;

    @OneToMany(mappedBy = "user")
    private Set<AccountDetails> accountDetails;

    @OneToMany(mappedBy = "user")
    private Set<Transaction> transactions;
}
