package com.example.netbankingapplication.data.repository;

import com.example.netbankingapplication.data.model.Transaction;
import com.example.netbankingapplication.data.model.TransactionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionJPA  extends JpaRepository<Transaction, TransactionId> {

    List<Transaction> findAllById_AccountId(Long from_account);

    @Procedure(name = "transfer_money")
    Integer transfer_money(@Param("from_account") Long from_account, @Param("to_account") Long to_account, @Param("amount") BigDecimal amount);
}
