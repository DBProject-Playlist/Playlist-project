package com.example.musicstore.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//import com.example.musicstore.repository.GenreRepository;
//import com.example.musicstore.entity.Genre;
import com.example.musicstore.repository.TrackRepository;
import com.example.musicstore.entity.Artist;
import com.example.musicstore.entity.Album;
import com.example.musicstore.entity.Track;
import com.example.musicstore.repository.ArtistRepository;
import com.example.musicstore.repository.AlbumRepository;


import org.springframework.stereotype.Controller;
//import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

//import java.nio.charset.StandardCharsets;
//import org.springframework.web.bind.annotation.*;
import java.util.List;

//import com.example.musicstore.entity.Album;
//import com.example.musicstore.entity.Artist;
//import com.example.musicstore.entity.Track;


@Controller
public class ArtistController {

    private final ArtistRepository artistRepository;
    private final AlbumRepository albumRepository;
    private final TrackRepository trackRepository;

    public ArtistController(ArtistRepository artistRepository,
                            AlbumRepository albumRepository,
                            TrackRepository trackRepository) {
        this.artistRepository = artistRepository;
        this.albumRepository = albumRepository;
        this.trackRepository = trackRepository;
    }

    // Display top popular artists
    @GetMapping("/popularArtists")
    public String showPopularArtists(Model model) {
    List<String> popularArtists = artistRepository.findPopularArtists();
    model.addAttribute("popularArtists", popularArtists);
    return "popularArtists"; 
}
    


    // Existing artist details
    @GetMapping("/artist/{name}")
    public String showArtistDetails(
            @PathVariable String name,
            @RequestParam(required = false, defaultValue = "false") boolean loadAll,
            Model model) {

        Artist artist = artistRepository.findByName(name);
        if (artist == null) {
            return "redirect:/artists"; // If artist not found
        }

        List<Album> albums = albumRepository.findByArtist(artist);

        List<Track> tracks;
        if (loadAll) {
            tracks = trackRepository.findAllTracksByArtist(name);
        } else {
            tracks = trackRepository.findTopTracksByArtist(name);
        }

        model.addAttribute("artistName", name);
        model.addAttribute("albums", albums);
        model.addAttribute("tracks", tracks);
        model.addAttribute("loadAll", loadAll);

        return "artistDetails";
    }
}
