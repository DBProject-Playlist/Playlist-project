package com.example.musicstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.musicstore.repository.TrackRepository;


import java.util.List;

@Controller
public class TrackController {

    @Autowired
    private TrackRepository trackRepository;

    @GetMapping("/popularTracks")
    public String getPopularTracks(Model model) {
        // Call repository
        List<Object[]> tracks = trackRepository.findPopularTracks();

        // Add to model
        model.addAttribute("tracks", tracks);

        return "popularTracks"; 
    }
}
