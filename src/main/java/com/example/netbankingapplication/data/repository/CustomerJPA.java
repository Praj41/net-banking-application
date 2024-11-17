package com.example.netbankingapplication.data.repository;

import com.example.netbankingapplication.data.model.Customer;
import com.example.netbankingapplication.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerJPA extends JpaRepository<Customer, Long> {
    Optional<Customer> findByUser(User user);
}
