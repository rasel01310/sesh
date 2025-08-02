package com.bloodDonation.redlife.repository;

import com.bloodDonation.redlife.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
 
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
} 