package com.bloodDonation.redlife.repository;

import com.bloodDonation.redlife.entity.DonationPost;
import com.bloodDonation.redlife.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DonationPostRepository extends JpaRepository<DonationPost, Long> {
    List<DonationPost> findByDonor(User donor);
} 