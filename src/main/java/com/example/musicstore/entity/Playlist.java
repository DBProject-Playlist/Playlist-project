package com.example.musicstore.entity;

import jakarta.persistence.*;
//import java.util.*;

@Entity
@Table(name = "PLAYLIST")
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "playlist_seq")
    @SequenceGenerator(name = "playlist_seq", sequenceName = "PLAYLIST_SEQ", allocationSize = 1)
    @Column(name = "PLAYLIST_ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @ManyToOne
    @JoinColumn(name = "CUSTOMER_ID")
    private User user;

    public Playlist() {}

    public Playlist(String name, User user) {
        this.name = name;
        this.user = user;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}

