package com.example.musicstore.controller;

import com.example.musicstore.entity.Playlist;
import com.example.musicstore.entity.User;
import com.example.musicstore.repository.AlbumRepository;
import com.example.musicstore.repository.ArtistRepository;
import com.example.musicstore.repository.GenreRepository;
import com.example.musicstore.repository.TrackRepository;
import com.example.musicstore.repository.PlaylistRepository;
import com.example.musicstore.repository.UserRepository;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Map;

@Controller
public class HomeController {

    private final GenreRepository genreRepo;
    private final ArtistRepository artistRepo;
    private final AlbumRepository albumRepo;
    private final TrackRepository trackRepo;
    private final PlaylistRepository playlistRepo;
    private final UserRepository userRepo;

    public HomeController(GenreRepository genreRepo,
                          ArtistRepository artistRepo,
                          AlbumRepository albumRepo,
                          TrackRepository trackRepo,
                          PlaylistRepository playlistRepo,
                          UserRepository userRepo) {
        this.genreRepo = genreRepo;
        this.artistRepo = artistRepo;
        this.albumRepo = albumRepo;
        this.trackRepo = trackRepo;
        this.playlistRepo = playlistRepo;
        this.userRepo = userRepo;
    }

    //HOME PAGE
    @GetMapping("/home")
    public String home(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Boolean loggedIn = (Boolean) session.getAttribute("loggedIn");
        loggedIn = loggedIn != null && loggedIn;
        model.addAttribute("loggedIn", loggedIn);

        // Common data
        model.addAttribute("genres", genreRepo.findAll());
        model.addAttribute("artists", artistRepo.findAll());
        model.addAttribute("albums", albumRepo.findAll());
        model.addAttribute("tracks", trackRepo.findAll());

        // Popular tracks and artists
        List<Object[]> popularTracks = trackRepo.findPopularTracks();
        model.addAttribute("popularTracks", popularTracks);

        List<String> top5Artists = artistRepo.findPopularArtists()
                .stream()
                .limit(5)
                .toList();
        model.addAttribute("top5Artists", top5Artists);

        //Show playlists only if logged in
        if (loggedIn && user != null) {
            List<Playlist> myPlaylists = playlistRepo.findByUser(user);
            model.addAttribute("myPlaylists", myPlaylists);
        }

        //Public top playlists (for everyone)
        List<Object[]> playlists = playlistRepo.findTop8Playlists();
        model.addAttribute("playlists", playlists);

        return "index";
    }

    //SIMPLE LOGIN
    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        HttpSession session) {

        Optional<User> userOpt = userRepo.findByEmail(email);

        if (userOpt.isPresent() && userOpt.get().getPassword().equals(password)) {
            session.setAttribute("user", userOpt.get());
            session.setAttribute("loggedIn", true);
        }
        return "redirect:/home";
    }

    //LOGOUT
    /*@GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/home";
    } */

    // GENRES
    @GetMapping("/genres")
    public String genres(Model model) {
        model.addAttribute("genres", genreRepo.findAll());
        return "genres";
    }

    // ARTISTS
    @GetMapping("/artists")
    public String artists(Model model) {
        model.addAttribute("artists", artistRepo.findAll());
        return "artists";
    }

    // ALBUMS
    @GetMapping("/albums")
    public String albums(Model model) {
        model.addAttribute("albums", albumRepo.findAll());
        return "albums";
    }

    // TRACKS
    @GetMapping("/tracks")
    public String tracks(Model model) {
        model.addAttribute("tracks", trackRepo.findAll());
        return "tracks";
    }


    @GetMapping("/playlists/{id}")
    public String getPlaylistDetails(@PathVariable("id") Long id, Model model) {
        List<Object[]> results = playlistRepo.findTracksByPlaylistId(id);
        
        List<Map<String, Object>> tracks = new ArrayList<>();
        for (Object[] row : results) {
            Map<String, Object> track = new HashMap<>();
            track.put("trackId", row[0]);
            track.put("name", row[1]);
            track.put("artist", row[2]);
            tracks.add(track);
        }

        model.addAttribute("tracks", tracks);
        model.addAttribute("playlist", playlistRepo.findById(id).orElse(null));
        return "playlistDetails";
    }


}
