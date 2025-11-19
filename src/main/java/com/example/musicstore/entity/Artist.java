package com.example.musicstore.entity;


import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Artist")
public class Artist {

    @Id
    @Column(name = "artist_id")
    private Long id;

    @Column(name = "Name")
    private String name;

    @OneToMany(mappedBy = "artist")
    private List<Album> albums;

    // Constructors
    public Artist() {}
    public Artist(Long id, String name) { this.id = id; this.name = name; }

    // Getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<Album> getAlbums() { return albums; }
    public void setAlbums(List<Album> albums) { this.albums = albums; }
}

