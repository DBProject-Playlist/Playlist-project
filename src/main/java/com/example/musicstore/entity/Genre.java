package com.example.musicstore.entity;
import jakarta.persistence.*;

@Entity
@Table(name = "genre")
public class Genre {

    @Id
    @Column(name = "genre_id")
    private Long id;

    @Column(name = "Name")
    private String name;

    // Constructors
    public Genre() {}
    public Genre(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}