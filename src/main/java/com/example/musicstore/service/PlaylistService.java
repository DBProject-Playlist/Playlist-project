package com.example.musicstore.service;

import com.example.musicstore.entity.Playlist;
import com.example.musicstore.entity.User;
import com.example.musicstore.repository.PlaylistRepository;
import com.example.musicstore.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlaylistService {

    private final PlaylistRepository playlistRepo;
    private final UserRepository userRepo;

    public PlaylistService(PlaylistRepository playlistRepo, UserRepository userRepo) {
        this.playlistRepo = playlistRepo;
        this.userRepo = userRepo;
    }

    @Transactional
    public Playlist createPlaylistForUser(Long userId, String playlistName) {
        User user = userRepo.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

        Playlist playlist = new Playlist();
        playlist.setName(playlistName);
        playlist.setUser(user);

        return playlistRepo.save(playlist);
    }
}
