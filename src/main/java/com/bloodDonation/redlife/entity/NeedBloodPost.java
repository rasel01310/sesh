package com.bloodDonation.redlife.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "need_blood_posts")
public class NeedBloodPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "receiver_id")
    private User receiver;

    private String division;
    private String district;
    private String area;
    private String bloodGroup;
    private String note;
    private boolean hidePhoneNumber;
    private LocalDateTime createdAt;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public User getReceiver() { return receiver; }
    public void setReceiver(User receiver) { this.receiver = receiver; }
    public String getDivision() { return division; }
    public void setDivision(String division) { this.division = division; }
    public String getDistrict() { return district; }
    public void setDistrict(String district) { this.district = district; }
    public String getArea() { return area; }
    public void setArea(String area) { this.area = area; }
    public String getBloodGroup() { return bloodGroup; }
    public void setBloodGroup(String bloodGroup) { this.bloodGroup = bloodGroup; }
    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
    public boolean isHidePhoneNumber() { return hidePhoneNumber; }
    public void setHidePhoneNumber(boolean hidePhoneNumber) { this.hidePhoneNumber = hidePhoneNumber; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
} 