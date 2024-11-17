package com.example.netbankingapplication.data.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "account_holder")
public class AccountHolders {

    @EmbeddedId
    private AccountHolderId id;

    @ManyToOne
    @MapsId("customerId")  // Maps the customerId part of the composite key
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    private Customer customer;

    @ManyToOne
    @MapsId("accountId")  // Maps the accountId part of the composite key
    @JoinColumn(name = "account_id", referencedColumnName = "account_id")
    private AccountDetails accountDetails;

}