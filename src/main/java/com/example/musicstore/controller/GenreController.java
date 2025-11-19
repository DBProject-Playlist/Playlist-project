package com.example.musicstore.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//import com.example.musicstore.repository.GenreRepository;
//import com.example.musicstore.entity.Genre;
import com.example.musicstore.repository.TrackRepository;

import org.springframework.stereotype.Controller;
//import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;

//import java.util.List;

@Controller
public class GenreController {

    private final TrackRepository trackRepo;

    public GenreController(TrackRepository trackRepo) {
        this.trackRepo = trackRepo;
    }

    @GetMapping("/genre/{name}")
    public String showTracksByGenre(
            @PathVariable String name,
            @RequestParam(defaultValue = "1") int page,
            Model model) {

        int pageSize = 10;
        int offset = (page - 1) * pageSize;

        var tracks = trackRepo.findByGenreNameWithLimitOffset(name, pageSize, offset);
        long totalTracks = trackRepo.countByGenreName(name);
        int totalPages = (int) Math.ceil((double) totalTracks / pageSize);

        model.addAttribute("genreName", name);
        model.addAttribute("tracks", tracks);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        return "genreDetails";
    }
}

