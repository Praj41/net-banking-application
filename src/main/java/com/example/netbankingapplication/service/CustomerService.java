package com.example.netbankingapplication.service;

import com.example.netbankingapplication.data.model.Customer;
import com.example.netbankingapplication.data.model.User;
import com.example.netbankingapplication.data.repository.CustomerJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CustomerService {

    @Autowired
    CustomerJPA customerJPA;

    @Autowired
    AccountService accountService;

    public String createCustomer(User user) {

        Customer customer = new Customer();

        customer.setName("Prajyot Bhamare");
        customer.setEmail(user.getEmail());
        customer.setMobileNo(user.getPhoneNumber());
        customer.setUser(user);
        customer.setAddress("Pune");
        customer.setAccounts(Set.of(accountService.findAccountByUser(user)));

        customerJPA.save(customer);

        return "Created";
    }

    public Customer findCustomer(Long id) {
        return customerJPA.findById(id).orElseGet(Customer::new);
    }

    public Customer findCustomerByUser(User user) {
        return customerJPA.findByUser(user).orElseGet(Customer::new);
    }

}
