package com.bloodDonation.redlife.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String phoneNumber;
    private String email;
    private String division;
    private String district;
    private String bloodGroup;
    private String role; // Donor or Receiver
    private String password;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getDivision() { return division; }
    public void setDivision(String division) { this.division = division; }
    public String getDistrict() { return district; }
    public void setDistrict(String district) { this.district = district; }
    public String getBloodGroup() { return bloodGroup; }
    public void setBloodGroup(String bloodGroup) { this.bloodGroup = bloodGroup; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
} 