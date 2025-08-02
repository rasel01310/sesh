package com.bloodDonation.redlife.service;

import com.bloodDonation.redlife.entity.User;
import com.bloodDonation.redlife.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User registerUser(User user) {
        // You can add password hashing and validation here
        return userRepository.save(user);
    }
} 