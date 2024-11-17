package com.example.netbankingapplication.data.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "account_details")
public class AccountDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long accountId;

    @Column(name = "balance")
    private BigDecimal balance;

    @Column(name = "type")
    private String type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @ManyToMany
    @JoinTable(
            name = "account_holder",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id")
    )
    private Set<Customer> customers;

    @OneToMany(mappedBy = "accountDetails")
    private Set<AccountHolders> customerAccounts;

    @OneToMany(mappedBy = "accountDetails")
    private Set<Transaction> transactions;

    @Override
    public String toString() {
        return "AccountDetails{" +
                "accountId=" + accountId +
                ", balance=" + balance +
                ", type='" + type + '\'' +
                '}';
    }
}
