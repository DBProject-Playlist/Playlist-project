package com.example.musicstore.controller;
import com.example.musicstore.entity.User;
import com.example.musicstore.repository.UserRepository;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    // Show Login page
    @GetMapping("/login")
    public String showLoginForm() {
        return "login"; // login.html
    }

    

    // Logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/home";
    }

    // Show forgot password page
    @GetMapping("/forgotpassword")
    public String showForgotPassword() {
        return "forgotPassword"; 
    }

    // Handle form submission
    @PostMapping("/forgotpassword")
    public String resetPassword(@RequestParam String email, 
                                @RequestParam String newPassword,
                                Model model) {
        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setPassword(newPassword);
            userRepository.save(user);
            model.addAttribute("message", "Password reset successfully!");
        } else {
            model.addAttribute("error", "Email not found!");
        }

        return "forgotPassword"; 
    }

} 
