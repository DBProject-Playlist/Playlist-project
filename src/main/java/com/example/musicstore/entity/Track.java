package com.example.musicstore.entity;


import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "Track")
public class Track {

    @Id
    @Column(name = "track_id")
    private Long id;

    @Column(name = "Name")
    private String name;

    @Column(name = "Composer")
    private String composer;

    @Column(name = "Milliseconds")
    private Long milliseconds;

    @Column(name = "Bytes")
    private Long bytes;

    @Column(name = "unit_price")
    private Double unitPrice;

    @ManyToOne
    @JoinColumn(name = "album_id", referencedColumnName = "album_id")
    private Album album;

    @ManyToOne
    @JoinColumn(name = "genre_id", referencedColumnName = "genre_id")
    private Genre genre;

    @ManyToOne
    @JoinColumn(name = "media_type_id", referencedColumnName = "media_type_id")
    private MediaType mediaType;

    @OneToMany(mappedBy = "track")
    private List<PlaylistTrack> playlistTracks;

    // Constructors
    public Track() {}
    public Track(Long id, String name, String composer, Long milliseconds, Long bytes, Double unitPrice,
                 Album album, Genre genre, MediaType mediaType) {
        this.id = id;
        this.name = name;
        this.composer = composer;
        this.milliseconds = milliseconds;
        this.bytes = bytes;
        this.unitPrice = unitPrice;
        this.album = album;
        this.genre = genre;
        this.mediaType = mediaType;
    }

    // Getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getComposer() { return composer; }
    public void setComposer(String composer) { this.composer = composer; }

    public Long getMilliseconds() { return milliseconds; }
    public void setMilliseconds(Long milliseconds) { this.milliseconds = milliseconds; }

    public Long getBytes() { return bytes; }
    public void setBytes(Long bytes) { this.bytes = bytes; }

    public Double getUnitPrice() { return unitPrice; }
    public void setUnitPrice(Double unitPrice) { this.unitPrice = unitPrice; }


    public Album getAlbum() { return album; }
    public void setAlbum(Album album) { this.album = album; }

    public Genre getGenre() { return genre; }
    public void setGenre(Genre genre) { this.genre = genre; }

    public MediaType getMediaType() { return mediaType; }
    public void setMediaType(MediaType mediaType) { this.mediaType = mediaType; }

    public List<PlaylistTrack> getPlaylistTracks() { return playlistTracks; }
    public void setPlaylistTracks(List<PlaylistTrack> playlistTracks) { this.playlistTracks = playlistTracks; }
}
