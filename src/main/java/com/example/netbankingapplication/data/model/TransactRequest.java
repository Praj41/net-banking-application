package com.example.netbankingapplication.data.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactRequest {

    private Long accountId;

    private Long destinationAccount;

    private BigDecimal amount;

    private String description;

}
