package com.example.musicstore.controller;

import com.example.musicstore.entity.Artist;
import com.example.musicstore.entity.Track;
import com.example.musicstore.entity.Album;
import com.example.musicstore.repository.ArtistRepository;
import com.example.musicstore.repository.TrackRepository;
import com.example.musicstore.repository.AlbumRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SearchController {

    private final ArtistRepository artistRepository;
    private final TrackRepository trackRepository;
    private final AlbumRepository albumRepository;

    public SearchController(ArtistRepository artistRepository,
                            TrackRepository trackRepository,
                            AlbumRepository albumRepository) {
        this.artistRepository = artistRepository;
        this.trackRepository = trackRepository;
        this.albumRepository = albumRepository;
    }

    @GetMapping("/search")
    public String search(@RequestParam("q") String query, Model model) {
        List<Artist> artists = artistRepository.findByNameContainingIgnoreCase(query);
        List<Track> tracks = trackRepository.findByNameContainingIgnoreCase(query);
        List<Album> albums = albumRepository.findByTitleContainingIgnoreCase(query);

        model.addAttribute("query", query);
        model.addAttribute("artists", artists);
        model.addAttribute("tracks", tracks);
        model.addAttribute("albums", albums);

        return "searchResults";
    }
}
