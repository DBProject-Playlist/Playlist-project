package com.example.musicstore.controller;

import com.example.musicstore.entity.User;
import com.example.musicstore.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;


@Controller
public class SignUpController {

    
    private final UserRepository userRepository;

    public SignUpController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/signup")
    public String signUpPage(Model model) {
        model.addAttribute("user", new User());
        return "signUp";
    }

    @PostMapping("/signup")
    public String signUpSubmit(@ModelAttribute User user) {
        userRepository.save(user); // ID will be generated automatically
        return "redirect:/login";
}

}


