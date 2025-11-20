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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.http.ResponseEntity;
import com.example.musicstore.entity.PlaylistTrack;
import com.example.musicstore.entity.Track;
import com.example.musicstore.repository.TrackRepository;
//import org.springframework.web.bind.annotation.PathVariable;

//import java.util.ArrayList;
//import java.util.HashMap;
import java.util.*;

import jakarta.transaction.Transactional;
//import java.util.Map;
 

@Controller
public class PlaylistController {

    private final PlaylistRepository playlistRepository;
    private final PlaylistTrackRepository playlistTrackRepository;
    private final TrackRepository trackRepository;

    @Autowired
    public PlaylistController(PlaylistRepository playlistRepository,
                              PlaylistTrackRepository playlistTrackRepository,
                              TrackRepository trackRepository) {
        this.playlistRepository = playlistRepository;
        this.playlistTrackRepository = playlistTrackRepository;
        this.trackRepository = trackRepository;
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

    @PostMapping("/playlists/create")
    public String createPlaylist(@RequestParam String playlistName, HttpSession session) {
        User user = (User) session.getAttribute("user");
        
        if (user == null) {
            return "redirect:/login";
        }

        if (playlistName != null && !playlistName.trim().isEmpty()) {
            Playlist playlist = new Playlist();
            playlist.setName(playlistName.trim());
            playlist.setUser(user);
            playlistRepository.save(playlist);
        }

        return "redirect:/playlists";
    }

    // Get user's playlists as JSON (for AJAX)
    @GetMapping("/api/playlists/my")
    @ResponseBody
    public ResponseEntity<List<Map<String, Object>>> getMyPlaylists(HttpSession session) {
        User user = (User) session.getAttribute("user");
        
        if (user == null) {
            return ResponseEntity.ok(Collections.emptyList());
        }

        List<Playlist> playlists = playlistRepository.findByUser(user);
        List<Map<String, Object>> result = new ArrayList<>();
        
        for (Playlist playlist : playlists) {
            Map<String, Object> playlistMap = new HashMap<>();
            playlistMap.put("id", playlist.getId());
            playlistMap.put("name", playlist.getName());
            result.add(playlistMap);
        }

        return ResponseEntity.ok(result);
    }

    // Add track to playlist
    @PostMapping("/api/playlists/add-track")
    @ResponseBody
    public ResponseEntity<String> addTrackToPlaylist(
            @RequestParam String trackName,
            @RequestParam Long playlistId,
            HttpSession session) {
        
        User user = (User) session.getAttribute("user");
        
        if (user == null) {
            return ResponseEntity.ok("Please log in to add tracks to playlists.");
        }

        // Find track by name
        List<Track> tracks = trackRepository.findByNameContainingIgnoreCase(trackName);
        if (tracks.isEmpty()) {
            return ResponseEntity.ok("Track not found: " + trackName);
        }

        // Use first matching track (exact match preferred)
        Track track = tracks.stream()
                .filter(t -> t.getName().equalsIgnoreCase(trackName))
                .findFirst()
                .orElse(tracks.get(0));

        // Verify playlist belongs to user
        Playlist playlist = playlistRepository.findById(playlistId).orElse(null);
        if (playlist == null) {
            return ResponseEntity.ok("Playlist not found.");
        }

        if (!playlist.getUser().getId().equals(user.getId())) {
            return ResponseEntity.ok("You can only add tracks to your own playlists.");
        }

        // Check if track already exists in playlist
        PlaylistTrack existing = playlistTrackRepository.findByPlaylistIdAndTrackId(playlistId, track.getId());

        if (existing != null) {
            return ResponseEntity.ok("Track already exists in this playlist.");
        }

        // Add track to playlist
        PlaylistTrack playlistTrack = new PlaylistTrack(playlistId, track.getId());
        playlistTrackRepository.save(playlistTrack);

        return ResponseEntity.ok("Track added to playlist successfully!");
    }

    @PostMapping("/api/playlists/remove-track")
    @ResponseBody
    @Transactional
    public ResponseEntity<String> removeTrackFromPlaylist(
            @RequestParam Long playlistId,
            @RequestParam Long trackId,
            HttpSession session) {

        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.ok("Please log in to remove tracks from playlists.");
        }

        Playlist playlist = playlistRepository.findById(playlistId).orElse(null);
        if (playlist == null) {
            return ResponseEntity.ok("Playlist not found.");
        }

        if (!playlist.getUser().getId().equals(user.getId())) {
            return ResponseEntity.ok("You can only modify your own playlists.");
        }

        PlaylistTrack existing = playlistTrackRepository.findByPlaylistIdAndTrackId(playlistId, trackId);
        if (existing == null) {
            return ResponseEntity.ok("Track not found in this playlist.");
        }

        playlistTrackRepository.delete(existing);
        return ResponseEntity.ok("Track removed from playlist.");
    }

    @PostMapping("/api/playlists/delete")
    @ResponseBody
    @Transactional
    public ResponseEntity<String> deletePlaylist(
            @RequestParam Long playlistId,
            HttpSession session) {

        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.ok("Please log in to delete playlists.");
        }

        Playlist playlist = playlistRepository.findById(playlistId).orElse(null);
        if (playlist == null) {
            return ResponseEntity.ok("Playlist not found.");
        }

        if (playlist.getUser() == null || !playlist.getUser().getId().equals(user.getId())) {
            return ResponseEntity.ok("You can only delete your own playlists.");
        }

        playlistTrackRepository.deleteByPlaylistId(playlistId);
        playlistTrackRepository.deleteLegacyPlaylistTracks(playlistId);
        playlistRepository.delete(playlist);
        return ResponseEntity.ok("Playlist deleted successfully.");
    }




}
