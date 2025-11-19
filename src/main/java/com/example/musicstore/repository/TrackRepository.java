package com.example.musicstore.repository;


import com.example.musicstore.entity.Album;
//import com.example.demo.entity.Album;
import com.example.musicstore.entity.Track;

//import org.springframework.boot.autoconfigure.flyway.FlywayProperties.Oracle;
//import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//mport org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TrackRepository extends JpaRepository<Track, Long> {

    // Pageable version (Oracle-safe)
    //List<Track> findAllByOrderByTrackIdAsc(Pageable pageable);

    // Native query with ROWNUM
    //@Query(value = "SELECT * FROM track t WHERE ROWNUM <= :limit ORDER BY track_id", nativeQuery = true)
    //List<Track> findTopTracks(@Param("limit") int limit);

    // Find tracks by Album
    //List<Track> findByAlbumAlbumId(Long albumId);

    // Find tracks by Genre
    //List<Track> findByGenreGenreId(Long genreId);

    //List<Track> findByGenre_Name(String name);
   // Pagination query using ROW_NUMBER()

   List<Track> findByNameContainingIgnoreCase(String name);
   
   @Query(value = "SELECT * FROM ( " +
                  "  SELECT t.*, ROW_NUMBER() OVER (ORDER BY t.track_id) AS rn " +
                  "  FROM track t " +
                  "  JOIN genre g ON t.genre_id = g.genre_id " +
                  "  WHERE LOWER(g.name) = LOWER(:name) " + 
                  ") " +
                  "WHERE rn > :offset AND rn <= (:offset + :limit)", nativeQuery = true)
    List<Track> findByGenreNameWithLimitOffset(
            @Param("name") String name,
            @Param("limit") int limit,
            @Param("offset") int offset);

    @Query(value = "SELECT COUNT(*) FROM track t " +
                   "JOIN genre g ON t.genre_id = g.genre_id " +
                   "WHERE LOWER(g.name) = LOWER(:name)", nativeQuery = true)
    long countByGenreName(@Param("name") String name);

    /* 
    // Top 5 popular tracks by artist
    @Query(value = "SELECT * FROM track t " +
                    "JOIN album a ON t.album_id = a.album_id " +
                    "JOIN artist ar ON a.artist_id = ar.artist_id " +
                    "WHERE LOWER(ar.name) = LOWER(:name) " +
                    "ORDER BY t.unit_price DESC " +
                    "FETCH FIRST 5 ROWS ONLY", nativeQuery = true)
    List<Track> findTop5ByArtistName(@Param("name") String name);
    
    // Pagination for "Load More" button
    @Query(value = "SELECT * FROM ( " +
                    "  SELECT t.*, ROW_NUMBER() OVER (ORDER BY t.track_id) AS rn " +
                    "  FROM track t " +
                    "  JOIN album a ON t.album_id = a.album_id " +
                    "  JOIN artist ar ON a.artist_id = ar.artist_id " +
                    "  WHERE LOWER(ar.name) = LOWER(:name) " +
                    ") " +
                    "WHERE rn > :offset AND rn <= (:offset + :limit)", nativeQuery = true)
    List<Track> findByArtistNameWithLimitOffset(@Param("name") String name,
                                                    @Param("limit") int limit,
                                                    @Param("offset") int offset);
    
    @Query(value = "SELECT COUNT(*) FROM track t " +
                   "JOIN album a ON t.album_id = a.album_id " +
                   "JOIN artist ar ON a.artist_id = ar.artist_id " +
                   "WHERE LOWER(ar.name) = LOWER(:name)", nativeQuery = true)
    long countByArtistName(@Param("name") String name);
    
    
    @Query(value = "SELECT * FROM track t " +
                   "JOIN album a ON t.album_id = a.album_id " +
                   "JOIN artist ar ON a.artist_id = ar.artist_id " +
                   "WHERE LOWER(ar.name) = LOWER(:name) " +
                   "ORDER BY t.unit_price DESC " +
                   "FETCH FIRST 5 ROWS ONLY", nativeQuery = true)
    List<Track> findTopTracksByArtist(@Param("name") String name);
*/
   // Top 5 tracks (Oracle requires ROWNUM)
    @Query(value = "SELECT * FROM ( " +
                   "  SELECT t.* FROM track t " +
                   "  JOIN album a ON t.album_id = a.album_id " +
                   "  JOIN artist ar ON a.artist_id = ar.artist_id " +
                   "  WHERE LOWER(ar.name) = LOWER(:name) " +
                   "  ORDER BY t.unit_price DESC " +
                   ") WHERE ROWNUM <= 5", nativeQuery = true)
    List<Track> findTopTracksByArtist(@Param("name") String name);

    // All tracks by artist (no limit, works in Oracle)
    @Query(value = "SELECT t.* FROM track t " +
                   "JOIN album a ON t.album_id = a.album_id " +
                   "JOIN artist ar ON a.artist_id = ar.artist_id " +
                   "WHERE LOWER(ar.name) = LOWER(:name) " +
                   "ORDER BY t.unit_price DESC", nativeQuery = true)
    List<Track> findAllTracksByArtist(@Param("name") String name);
    
    /*
    @Query(value = "SELECT * FROM track t " +
                   "JOIN album a ON t.album_id = a.album_id " +
                   "JOIN artist ar ON a.artist_id = ar.artist_id " +
                   "WHERE LOWER(ar.name) = LOWER(:name) " +
                   "ORDER BY t.track_id", nativeQuery = true)
    List<Track> findByArtistName(@Param("name") String name);
    */
    // Find tracks by Album
    List<Track> findByAlbum(Album album);

    //top tracks



    @Query(value = """
        SELECT t.name,
               a.name AS artist_name,
               ROUND(t.milliseconds / 60000, 2) AS duration_minutes
        FROM (
            SELECT t.track_id, t.name, t.album_id, t.milliseconds, SUM(il.quantity) AS total_sold
            FROM Track t
            JOIN InvoiceLine il ON t.track_id = il.track_id
            GROUP BY t.track_id, t.name, t.album_id, t.milliseconds
        ) t
        JOIN Album al ON t.album_id = al.album_id
        JOIN Artist a ON al.artist_id = a.artist_id
        WHERE ROWNUM <= 50
        ORDER BY t.total_sold DESC
        """, nativeQuery = true)
    
    
    List<Object[]> findPopularTracks();
    

    
    }
    
    
    
   


