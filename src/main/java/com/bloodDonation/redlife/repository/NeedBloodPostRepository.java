package com.bloodDonation.redlife.repository;

import com.bloodDonation.redlife.entity.NeedBloodPost;
import com.bloodDonation.redlife.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NeedBloodPostRepository extends JpaRepository<NeedBloodPost, Long> {
    List<NeedBloodPost> findByReceiver(User receiver);
} 