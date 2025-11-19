package com.example.musicstore.entity;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "PlaylistTrack")
@IdClass(PlaylistTrack.PlaylistTrackId.class)
public class PlaylistTrack {

    @Id
    @Column(name = "playlist_id")
    private Long playlistId;

    @Id
    @Column(name = "track_id")
    private Long trackId;

    @ManyToOne
    @JoinColumn(name = "track_id", insertable = false, updatable = false)
    private Track track;

    @ManyToOne
    @JoinColumn(name = "playlist_id", insertable = false, updatable = false)
    private Playlist playlist;

    public Playlist getPlaylist() { return playlist; }
    public void setPlaylist(Playlist playlist) { this.playlist = playlist; }




    public PlaylistTrack() {}
    public PlaylistTrack(Long playlistId, Long trackId) { this.playlistId = playlistId; this.trackId = trackId; }

    public Long getPlaylistId() { return playlistId; }
    public void setPlaylistId(Long playlistId) { this.playlistId = playlistId; }

    public Long getTrackId() { return trackId; }
    public void setTrackId(Long trackId) { this.trackId = trackId; }

    public Track getTrack() { return track; }
    public void setTrack(Track track) { this.track = track; }


    

    public static class PlaylistTrackId implements Serializable {
        private Long playlistId;
        private Long trackId;

        public PlaylistTrackId() {}
        public PlaylistTrackId(Long playlistId, Long trackId) { this.playlistId = playlistId; this.trackId = trackId; }

        public Long getPlaylistId() { return playlistId; }
        public void setPlaylistId(Long playlistId) { this.playlistId = playlistId; }

        public Long getTrackId() { return trackId; }
        public void setTrackId(Long trackId) { this.trackId = trackId; }
    }
}
