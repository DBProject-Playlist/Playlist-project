package com.example.musicstore.entity;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "CUSTOMER") // match Oracle table name
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_seq_gen")
    @SequenceGenerator(name = "customer_seq_gen", sequenceName = "CUSTOMER_SEQ", allocationSize = 1)
    @Column(name = "CUSTOMER_ID")
    private Long id;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "SUPPORT_REP_ID")
    private Long supportRepId;

    @Column(name = "PASSWORD")
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Playlist> playlists;


    public User() {}

    // constructor without id (Hibernate will generate it)
    public User(String firstName, String lastName, String email, Long supportRepId, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.supportRepId = supportRepId;
        this.password = password;
    }

    // getters and setters...
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Long getSupportRepId() { return supportRepId; }
    public void setSupportRepId(Long supportRepId) { this.supportRepId = supportRepId; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
