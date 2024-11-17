package com.example.netbankingapplication.controllers;

import com.example.netbankingapplication.data.model.*;
import com.example.netbankingapplication.security.JwtUtil;
import com.example.netbankingapplication.service.AccountService;
import com.example.netbankingapplication.service.CustomerService;
import com.example.netbankingapplication.service.TransactionService;
import com.example.netbankingapplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class testController {

    @Autowired
    UserService userService;

    @Autowired
    AccountService accountService;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    TransactionService transactionService;

    @GetMapping(path = "/test1")
    public String test1() {
        return "Hello World secure!!!";
    }

    @GetMapping(path = "/test")
    public String test() {
        return "Hello World unsecure!!!";
    }

    @GetMapping(path = "/createAcc")
    public User create() {
        return userService.createDumyUser();
    }



    @PostMapping(path = "/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {

        System.out.println("loginRequest: " + loginRequest.getUsername() + " " + loginRequest.getPassword());

        User user = userService.findUserByUserName(loginRequest.getUsername());

        if (user == null) {
            return new LoginResponse("User not found", null);
        }
        
        if (!new BCryptPasswordEncoder().matches(loginRequest.getPassword(), user.getPassword())) {
            return new LoginResponse("Invalid password", null);
        } else {
            return new LoginResponse("Success", jwtUtil.generateToken(user.getUserName()));
        }

    }

    @PostMapping(path = "/createAccount")
    public String createAccount() {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //return accountService.assignAccountToUser(userService.findUserByUserName(user.getUsername()));
        return "disabled";
    }

    @GetMapping(path = "/getAccountHolderByUser")
    public AccountHolders getAccountHolderByUser() {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return accountService.findAccountHolder(userService.findUserByUserName(user.getUsername()));
    }

    @GetMapping(path = "/userinfo")
    public User getUserInfo() {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userEntity = userService.findUserByUserName(user.getUsername());
        userEntity.setPassword(null);
        userEntity.getTransactions().clear();
        userEntity.getAccountDetails().clear();
        return userEntity;
    }

    @PostMapping(path = "/transact")
    public String transact(@RequestBody TransactRequest request) {

        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        System.out.println("request: " + request.getAmount());
        System.out.println("user: " + user.getUsername());
        System.out.println("from: " + request.getDestinationAccount());
        System.out.println("from: " + request.getAccountId());

        transactionService.transferMoney(request.getAccountId(), request.getDestinationAccount(), request.getAmount());

        return "Transaction successful for user: " + user.getUsername();
    }

    @GetMapping(path = "/getUserAccounts")
    public List<AccountDetails> getUserAccounts() {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return accountService.getAccountIdList(userService.findUserByUserName(user.getUsername()));
    }

    @GetMapping(path = "/transactions")
    public List<Transaction> getTransactions(@RequestParam("accountId") Long accountId) {
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return transactionService.getTransactions(accountId);
    }

}
