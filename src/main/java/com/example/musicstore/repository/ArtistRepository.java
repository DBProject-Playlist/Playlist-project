package com.example.musicstore.repository;

import com.example.musicstore.entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
    Artist findByNameIgnoreCase(String name);
    //Artist findByNameIgnoreCase(String name);
    Artist findByName(String name);

    //popular artists
    @Query(value = "SELECT * FROM (" +
               "  SELECT ar.NAME " +
               "  FROM ARTIST ar " +
               "  JOIN ALBUM al ON ar.ARTIST_ID = al.ARTIST_ID " +
               "  JOIN TRACK t ON al.ALBUM_ID = t.ALBUM_ID " +
               "  JOIN INVOICELINE il ON t.TRACK_ID = il.TRACK_ID " +
               "  GROUP BY ar.ARTIST_ID, ar.NAME " +
               "  ORDER BY SUM(il.QUANTITY) DESC" +
               ") WHERE ROWNUM <= 50",
       nativeQuery = true)
    List<String> findPopularArtists();


    

    // Find artists by name (like search)
   List<Artist> findByNameContainingIgnoreCase(String name);

}