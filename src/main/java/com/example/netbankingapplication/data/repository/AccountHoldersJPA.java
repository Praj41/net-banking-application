package com.example.netbankingapplication.data.repository;

import com.example.netbankingapplication.data.model.AccountHolderId;
import com.example.netbankingapplication.data.model.AccountHolders;
import com.example.netbankingapplication.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountHoldersJPA  extends JpaRepository<AccountHolders, AccountHolderId> {
    public AccountHolders findByAccountDetails_User(User user);
}
