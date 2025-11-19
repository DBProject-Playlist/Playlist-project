package com.example.musicstore.repository;

import com.example.musicstore.entity.Playlist;
import com.example.musicstore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {

    @Query(value = """
        SELECT t.TRACK_ID AS trackId, t.NAME AS trackName, ar.NAME AS artistName
        FROM PLAYLIST p
        JOIN PLAYLISTTRACK pt ON p.PLAYLIST_ID = pt.PLAYLIST_ID
        JOIN TRACK t ON pt.TRACK_ID = t.TRACK_ID
        JOIN ALBUM a ON t.ALBUM_ID = a.ALBUM_ID
        JOIN ARTIST ar ON a.ARTIST_ID = ar.ARTIST_ID
        WHERE p.PLAYLIST_ID = :playlistId
        ORDER BY t.NAME
        """, nativeQuery = true)
    List<Object[]> findTracksByPlaylistId(@Param("playlistId") Long playlistId);

    
    //Recommend playlists
    @Query(
        value = "SELECT * FROM ( " +
                "SELECT p.playlist_id, p.Name, SUM(il.Quantity) AS total_sold " +
                "FROM Playlist p " +
                "JOIN PlaylistTrack pt ON p.playlist_id = pt.playlist_id " +
                "JOIN InvoiceLine il ON pt.track_id = il.track_id " +
                "GROUP BY p.playlist_id, p.Name " +
                "ORDER BY total_sold DESC " +
                ") " +
                "WHERE ROWNUM <= 8",
        nativeQuery = true
    )
    List<Object[]> findTop8Playlists();


    List<Playlist> findByUser(User user);
}

