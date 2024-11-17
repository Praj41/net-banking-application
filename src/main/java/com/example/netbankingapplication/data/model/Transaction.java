package com.example.netbankingapplication.data.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "transactions")
public class Transaction {

    @EmbeddedId
    private TransactionId id;

    @ManyToOne
    @MapsId("userId")  // Maps the userId part of the composite key
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @ManyToOne
    @MapsId("accountId")  // Maps the accountId part of the composite key
    @JoinColumn(name = "account_id", referencedColumnName = "account_id")
    private AccountDetails accountDetails;

    @Column(name = "amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = "transaction_date", nullable = false)
    private LocalDateTime transactionDate;

    @ManyToOne
    @JoinColumn(name = "to_account_id", referencedColumnName = "account_id")
    private AccountDetails receivingAccountId;

    public Transaction(TransactionId id, User user, AccountDetails accountDetails, BigDecimal amount, LocalDateTime transactionDate, AccountDetails receivingAccountId) {
        this.id = id;
        this.user = user;
        this.accountDetails = accountDetails;
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.receivingAccountId = receivingAccountId;
    }

    public String generateReferenceNo() {
        return UUID.randomUUID().toString();
    }
}
