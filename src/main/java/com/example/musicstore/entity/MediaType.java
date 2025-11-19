package com.example.musicstore.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "MediaType")
public class MediaType {

    @Id
    @Column(name = "media_type_id")
    private Long id;

    @Column(name = "Name")
    private String name;

    // Constructors
    public MediaType() {}
    public MediaType(Long id, String name) { this.id = id; this.name = name; }

    // Getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}

