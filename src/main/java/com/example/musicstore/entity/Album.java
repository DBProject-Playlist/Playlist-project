package com.example.musicstore.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Album")
public class Album {

    @Id
    @Column(name = "album_id")
    private Long id;

    @Column(name = "title")
    private String title;

    @ManyToOne
    @JoinColumn(name = "artist_id", referencedColumnName = "artist_id")
    private Artist artist;

    @OneToMany(mappedBy = "album")
    private List<Track> tracks;

    // Constructors
    public Album() {}
    public Album(Long id, String title, Artist artist) {
        this.id = id;
        this.title = title;
        this.artist = artist;
    }

    // Getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public Artist getArtist() { return artist; }
    public void setArtist(Artist artist) { this.artist = artist; }

    public List<Track> getTracks() { return tracks; }
    public void setTracks(List<Track> tracks) { this.tracks = tracks; }
}

