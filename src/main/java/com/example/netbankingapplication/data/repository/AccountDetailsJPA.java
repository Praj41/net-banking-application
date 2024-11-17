package com.example.netbankingapplication.data.repository;

import com.example.netbankingapplication.data.model.AccountDetails;
import com.example.netbankingapplication.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountDetailsJPA extends JpaRepository<AccountDetails, Long> {
    Optional<AccountDetails> findByUser(User user);

    List<AccountDetails> findAllByUser(User user);
}
