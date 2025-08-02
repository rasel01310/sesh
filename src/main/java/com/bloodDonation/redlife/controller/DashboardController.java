package com.bloodDonation.redlife.controller;

import com.bloodDonation.redlife.entity.DonationPost;
import com.bloodDonation.redlife.entity.NeedBloodPost;
import com.bloodDonation.redlife.entity.User;
import com.bloodDonation.redlife.repository.DonationPostRepository;
import com.bloodDonation.redlife.repository.NeedBloodPostRepository;
import com.bloodDonation.redlife.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class DashboardController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DonationPostRepository donationPostRepository;
    @Autowired
    private NeedBloodPostRepository needBloodPostRepository;

    private static final Logger logger = LoggerFactory.getLogger(DashboardController.class);

    @GetMapping("/donor-dashboard")
    public String donorDashboard(Model model, @AuthenticationPrincipal UserDetails userDetails, @RequestParam(value = "success", required = false) String success) {
        User donor = userRepository.findByEmail(userDetails.getUsername());
        model.addAttribute("user", donor);
        System.out.println("DEBUG: Donor phone number = " + donor.getPhoneNumber());
        List<DonationPost> myPosts = donationPostRepository.findByDonor(donor);
        List<DonationPost> allPosts = donationPostRepository.findAll();
        for (DonationPost post : allPosts) {
            if (post.getDonor() != null) {
                System.out.println("DEBUG: Post ID " + post.getId() + " Donor Phone = " + post.getDonor().getPhoneNumber());
            } else {
                System.out.println("DEBUG: Post ID " + post.getId() + " Donor is null");
            }
        }
        List<NeedBloodPost> needBloodPosts = needBloodPostRepository.findAll();
        for (NeedBloodPost post : needBloodPosts) {
            if (post.getReceiver() != null) {
                System.out.println("DEBUG: NeedBloodPost ID " + post.getId() + " Receiver Phone = " + post.getReceiver().getPhoneNumber());
            } else {
                System.out.println("DEBUG: NeedBloodPost ID " + post.getId() + " Receiver is null");
            }
        }
        model.addAttribute("myPosts", myPosts);
        model.addAttribute("allPosts", allPosts);
        model.addAttribute("needBloodPosts", needBloodPosts);
        if (success != null) model.addAttribute("success", success);
        return "donor-dashboard";
    }

    @PostMapping("/donor/create-post")
    public String createDonationPost(@AuthenticationPrincipal UserDetails userDetails,
                                     @RequestParam String division,
                                     @RequestParam String district,
                                     @RequestParam String area,
                                     @RequestParam(required = false) String note,
                                     @RequestParam(value = "hidePhoneNumber", required = false, defaultValue = "false") boolean hidePhoneNumber,
                                     Model model) {
        User donor = userRepository.findByEmail(userDetails.getUsername());
        if (donor == null) {
            model.addAttribute("error", "Donor user not found. Please log in again.");
            return "donor-dashboard";
        }
        try {
            DonationPost post = new DonationPost();
            post.setDonor(donor);
            post.setDivision(division);
            post.setDistrict(district);
            post.setArea(area);
            post.setNote(note);
            post.setHidePhoneNumber(hidePhoneNumber);
            post.setCreatedAt(LocalDateTime.now());
            donationPostRepository.save(post);
            logger.info("DonationPost saved: {} | Donor phone: {}", post.getId(), donor.getPhoneNumber());
        } catch (Exception e) {
            logger.error("Error saving DonationPost", e);
            model.addAttribute("error", "Error saving donation post: " + e.getMessage());
            return "donor-dashboard";
        }
        return "redirect:/donor-dashboard?success=Donation+post+created+successfully";
    }

    @GetMapping("/receiver-dashboard")
    public String receiverDashboard(Model model,
                                    @AuthenticationPrincipal UserDetails userDetails,
                                    @RequestParam(required = false) String bloodGroup,
                                    @RequestParam(required = false) String division,
                                    @RequestParam(required = false) String district,
                                    @RequestParam(value = "success", required = false) String success) {
        List<DonationPost> posts = donationPostRepository.findAll();
        if (bloodGroup != null && !bloodGroup.isEmpty()) {
            posts = posts.stream().filter(p -> p.getDonor().getBloodGroup().equalsIgnoreCase(bloodGroup)).toList();
        }
        if (division != null && !division.isEmpty()) {
            posts = posts.stream().filter(p -> p.getDivision().equalsIgnoreCase(division)).toList();
        }
        if (district != null && !district.isEmpty()) {
            posts = posts.stream().filter(p -> p.getDistrict().equalsIgnoreCase(district)).toList();
        }
        User receiver = userRepository.findByEmail(userDetails.getUsername());
        model.addAttribute("user", receiver);
        System.out.println("DEBUG: Receiver phone number = " + receiver.getPhoneNumber());
        List<NeedBloodPost> myNeedBloodPosts = needBloodPostRepository.findByReceiver(receiver);
        List<NeedBloodPost> needBloodPosts = needBloodPostRepository.findAll();
        for (NeedBloodPost post : needBloodPosts) {
            if (post.getReceiver() != null) {
                System.out.println("DEBUG: NeedBloodPost ID " + post.getId() + " Receiver Phone = " + post.getReceiver().getPhoneNumber());
            } else {
                System.out.println("DEBUG: NeedBloodPost ID " + post.getId() + " Receiver is null");
            }
        }
        model.addAttribute("posts", posts);
        model.addAttribute("myNeedBloodPosts", myNeedBloodPosts);
        model.addAttribute("needBloodPosts", needBloodPosts);
        model.addAttribute("selectedBloodGroup", bloodGroup);
        model.addAttribute("selectedDivision", division);
        model.addAttribute("selectedDistrict", district);
        if (success != null) model.addAttribute("success", success);
        return "receiver-dashboard";
    }

    @PostMapping("/receiver/create-need-blood-post")
    public String createNeedBloodPost(@AuthenticationPrincipal UserDetails userDetails,
                                      @RequestParam String bloodGroup,
                                      @RequestParam String division,
                                      @RequestParam String district,
                                      @RequestParam String area,
                                      @RequestParam(required = false) String note,
                                      @RequestParam(value = "hidePhoneNumber", required = false, defaultValue = "false") boolean hidePhoneNumber,
                                      Model model) {
        User receiver = userRepository.findByEmail(userDetails.getUsername());
        if (receiver == null) {
            model.addAttribute("error", "Receiver user not found. Please log in again.");
            return "receiver-dashboard";
        }
        try {
            NeedBloodPost post = new NeedBloodPost();
            post.setReceiver(receiver);
            post.setBloodGroup(bloodGroup);
            post.setDivision(division);
            post.setDistrict(district);
            post.setArea(area);
            post.setNote(note);
            post.setHidePhoneNumber(hidePhoneNumber);
            post.setCreatedAt(LocalDateTime.now());
            needBloodPostRepository.save(post);
            logger.info("NeedBloodPost saved: {} | Receiver phone: {}", post.getId(), receiver.getPhoneNumber());
        } catch (Exception e) {
            logger.error("Error saving NeedBloodPost", e);
            model.addAttribute("error", "Error saving need blood post: " + e.getMessage());
            return "receiver-dashboard";
        }
        return "redirect:/receiver-dashboard?success=Need+Blood+post+created+successfully";
    }

    @GetMapping("/admin-dashboard")
    public String adminDashboard(Model model) {
        List<User> donors = userRepository.findAll().stream().filter(u -> "Donor".equalsIgnoreCase(u.getRole())).toList();
        List<User> receivers = userRepository.findAll().stream().filter(u -> "Receiver".equalsIgnoreCase(u.getRole())).toList();
        List<DonationPost> posts = donationPostRepository.findAll();
        List<NeedBloodPost> needBloodPosts = needBloodPostRepository.findAll();
        model.addAttribute("donors", donors);
        model.addAttribute("receivers", receivers);
        model.addAttribute("posts", posts);
        model.addAttribute("needBloodPosts", needBloodPosts);
        return "admin-dashboard";
    }

    @PostMapping("/admin/delete-user")
    public String deleteUser(@RequestParam Long id) {
        userRepository.deleteById(id);
        return "redirect:/admin-dashboard";
    }

    @PostMapping("/admin/delete-donation-post")
    public String deleteDonationPost(@RequestParam Long id) {
        donationPostRepository.deleteById(id);
        return "redirect:/admin-dashboard";
    }

    @PostMapping("/admin/delete-need-blood-post")
    public String deleteNeedBloodPost(@RequestParam Long id) {
        needBloodPostRepository.deleteById(id);
        return "redirect:/admin-dashboard";
    }
} 