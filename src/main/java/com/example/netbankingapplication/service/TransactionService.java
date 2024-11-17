package com.example.netbankingapplication.service;

import com.example.netbankingapplication.data.model.Customer;
import com.example.netbankingapplication.data.model.Transaction;
import com.example.netbankingapplication.data.model.TransactionId;
import com.example.netbankingapplication.data.repository.AccountDetailsJPA;
import com.example.netbankingapplication.data.repository.CustomerJPA;
import com.example.netbankingapplication.data.repository.TransactionJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    TransactionJPA transactionRepo;

    @Autowired
    AccountDetailsJPA accountRepo;

    @Autowired
    CustomerJPA customerRepo;

    @Autowired
    private AccountDetailsJPA accountDetailsJPA;


    public String transferMoney(Long from_account, Long to_account, BigDecimal amount) {

        Integer status = transactionRepo.transfer_money(from_account, to_account, amount);

        if (status == 1) {
            return "Transaction Successful";
        } else  if (status == 0) {
            return "Insufficient Balance";
        } else {
            return "Transaction Failed";
        }
    }

    public List<Transaction> getTransactions(Long transactionId) {
        List<Transaction> transactions = transactionRepo.findAllById_AccountId(transactionId);

        transactions.forEach(transaction -> {
            transaction.setUser(null);
            transaction.setAccountDetails(null);
            transaction.getReceivingAccountId().setUser(null);
            transaction.getReceivingAccountId().setTransactions(null);
        });

        return transactions;
    }

}
