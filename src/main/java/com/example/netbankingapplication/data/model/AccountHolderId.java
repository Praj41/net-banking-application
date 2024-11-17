package com.example.netbankingapplication.data.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Embeddable
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AccountHolderId {

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "account_id")
    private Long accountId;
}