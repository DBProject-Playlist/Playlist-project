package com.example.musicstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.musicstore.entity.Track;
import com.example.musicstore.entity.PlaylistTrack;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.*;


public interface PlaylistTrackRepository extends JpaRepository<PlaylistTrack, Long> {

    @Query("SELECT t FROM Track t JOIN PlaylistTrack pt ON t.id = pt.trackId WHERE pt.playlistId = :playlistId")
    List<Track> findTracksByPlaylistId(@Param("playlistId") Long playlistId);


}
