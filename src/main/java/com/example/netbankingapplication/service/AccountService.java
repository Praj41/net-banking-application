package com.example.netbankingapplication.service;

import com.example.netbankingapplication.data.model.*;
import com.example.netbankingapplication.data.repository.AccountDetailsJPA;
import com.example.netbankingapplication.data.repository.AccountHoldersJPA;
import com.example.netbankingapplication.data.repository.CustomerJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountService {

    @Autowired
    AccountDetailsJPA accountDetailsJPA;

    @Autowired
    UserService userService;

    @Autowired
    CustomerJPA customerJPA;

    @Autowired
    AccountHoldersJPA accountHoldersJPA;

    public String createDummyAccount(User user) {
        AccountDetails acc = new AccountDetails();

        acc.setType("Saving");
        acc.setUser(user);
        acc.setBalance(new BigDecimal("123.45"));

        accountDetailsJPA.save(acc);

        return "Account created";
    }

    public AccountDetails findAccount(Long id) {
        return accountDetailsJPA.findById(id).orElseGet(AccountDetails::new);
    }

    public AccountDetails findAccountByUser(User user) {
        return accountDetailsJPA.findByUser(user).orElseGet(AccountDetails::new);
    }

    public List<AccountDetails> getAccountIdList(User user) {
        List<AccountDetails> accountDetails = accountDetailsJPA.findAllByUser(user);
        System.out.println(accountDetails.toString());

        accountDetails.forEach(account -> {
            account.setUser(null);
            account.setCustomerAccounts(null);
            account.setTransactions(null);
            account.setCustomers(null);
        });
        return accountDetails;
    }

    public AccountHolders findAccountHolder(User user) {
        return accountHoldersJPA.findByAccountDetails_User(user);
    }

    public String assignAccountToUser(User user) {
        AccountHolders accountHolders = new AccountHolders();

        Customer customer = customerJPA.findByUser(user).orElseGet(Customer::new);
        AccountDetails accountDetails = findAccountByUser(user);

        accountHolders.setCustomer(customer);
        accountHolders.setAccountDetails(accountDetails);

        accountHolders.setId(new AccountHolderId(customer.getCustomerId(), accountDetails.getAccountId()));

        accountHoldersJPA.save(accountHolders);

        return "Account assigned to user";
    }
}
