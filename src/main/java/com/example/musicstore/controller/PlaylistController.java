package com.example.musicstore.controller;

import com.example.musicstore.entity.Playlist;
//import com.example.musicstore.entity.Playlist;
import com.example.musicstore.entity.User;
import com.example.musicstore.repository.PlaylistRepository;
import com.example.musicstore.repository.PlaylistTrackRepository;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;

//import java.util.ArrayList;
//import java.util.HashMap;
import java.util.*;
//import java.util.Map;
 

@Controller
public class PlaylistController {

    private final PlaylistRepository playlistRepository;
    /*private final PlaylistTrackRepository playlistTrackRepository;*/

    @Autowired
    public PlaylistController(PlaylistRepository playlistRepository,
                              PlaylistTrackRepository playlistTrackRepository) {
        this.playlistRepository = playlistRepository;
        /*this.playlistTrackRepository = playlistTrackRepository;*/
    }

    /* 
    @GetMapping("/playlists")
    public String listPlaylists(Model model) {
        model.addAttribute("playlists", playlistRepository.findAll());
        return "playlists"; // template to show all playlists
    }

    @GetMapping("/playlists/{id}")
    public String playlistDetails(@PathVariable Long id, Model model) {
        Playlist playlist = playlistRepository.findById(id).orElse(null);
        if (playlist == null) {
            return "redirect:/playlists";
        }

        List<Track> tracks = playlistTrackRepository.findTracksByPlaylistId(id);
        System.out.println("Tracks found: " + tracks.size()); // debug

        model.addAttribute("playlist", playlist);
        model.addAttribute("tracks", tracks);
        return "playlistDetails"; // template to show tracks
    }
    */

    /*@GetMapping("/playlists")
    public String getAllPlaylists(Model model) {
        model.addAttribute("playlists", playlistRepository.findAll());
        return "playlists";
    }

    @GetMapping("/playlists/{id}")
    public String getPlaylistDetails(@PathVariable("id") Long id, Model model) {
        List<Object[]> results = playlistRepository.findTracksByPlaylistId(id);
        
        List<Map<String, Object>> tracks = new ArrayList<>();
        for (Object[] row : results) {
            Map<String, Object> track = new HashMap<>();
            track.put("trackId", row[0]);
            track.put("name", row[1]);
            track.put("artist", row[2]);
            tracks.add(track);
        }

        model.addAttribute("tracks", tracks);
        model.addAttribute("playlist", playlistRepository.findById(id).orElse(null));
        return "playlistDetails";
    } */

    @GetMapping("/playlists")
    public String showPlaylists(HttpSession session, Model model) {
        Boolean loggedIn = session.getAttribute("user") != null;
        model.addAttribute("loggedIn", loggedIn);

        if (loggedIn) {
            User user = (User) session.getAttribute("user");
            List<Playlist> myPlaylists = playlistRepository.findByUser(user);
            model.addAttribute("myPlaylists", myPlaylists);
        }else {
            model.addAttribute("myPlaylists", Collections.emptyList()); // optional safety
        }

        List<Playlist> allPlaylists = playlistRepository.findAll();
        model.addAttribute("playlists", allPlaylists);

        return "playlists";
    }




}
