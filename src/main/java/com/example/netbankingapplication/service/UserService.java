package com.example.netbankingapplication.service;

import com.example.netbankingapplication.data.model.User;
import com.example.netbankingapplication.data.repository.UserJPA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserJPA userJPA;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userJPA.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new org.springframework.security.core.userdetails.User(
                user.getUserName(), user.getPassword(), new ArrayList<>());
    }

    /**
     * Method to create a new user
     * @param user The user to be created
     * @return The saved user
     */
    public User createUser(User user) {
        // Hash the password before saving
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));

        // Save the user to the database
        return userJPA.save(user);
    }


    public User createDumyUser() {

        User user = new User();

        user.setUserName("prabhama");
        user.setEmail("pcbhamare.41@gmail.com");
        user.setPhoneNumber("8605996206");
        user.setPassword(new BCryptPasswordEncoder().encode("asdf1234"));

        userJPA.save(user);

        return user;
    }

    public User findUser(Long id) {
        return userJPA.findById(id).orElseGet(User::new);
    }

    public User findUserByUserName(String userName) {
        return userJPA.findByUserName(userName).orElseGet(null);
    }
}
