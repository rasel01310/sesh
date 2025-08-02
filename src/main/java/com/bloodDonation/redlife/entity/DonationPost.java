package com.bloodDonation.redlife.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "donation_posts")
public class DonationPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "donor_id")
    private User donor;

    private String division;
    private String district;
    private String area;
    private String note;
    private boolean hidePhoneNumber;
    private LocalDateTime createdAt;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public User getDonor() { return donor; }
    public void setDonor(User donor) { this.donor = donor; }
    public String getDivision() { return division; }
    public void setDivision(String division) { this.division = division; }
    public String getDistrict() { return district; }
    public void setDistrict(String district) { this.district = district; }
    public String getArea() { return area; }
    public void setArea(String area) { this.area = area; }
    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
    public boolean isHidePhoneNumber() { return hidePhoneNumber; }
    public void setHidePhoneNumber(boolean hidePhoneNumber) { this.hidePhoneNumber = hidePhoneNumber; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
} 