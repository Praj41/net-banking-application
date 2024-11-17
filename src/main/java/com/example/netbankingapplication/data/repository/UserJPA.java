package com.example.netbankingapplication.data.repository;

import com.example.netbankingapplication.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJPA extends JpaRepository<User, Long> {

    public Optional<User> findByUserName(String userName);
}
