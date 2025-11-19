package com.example.musicstore.repository;

import com.example.musicstore.entity.Album;
import com.example.musicstore.entity.Artist;

//import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AlbumRepository extends JpaRepository<Album, Long> {
    //List<Album> findByArtistArtistId(Long artistId);
    List<Album> findByArtist(Artist artist);

    // Sort albums alphabetically by title
    List<Album> findAllByOrderByTitleAsc();

    List<Album> findByTitleContainingIgnoreCase(String title);
    


   // Popular albums query
       @Query(value = "SELECT a.album_id, a.Title, ar.artist_id, ar.Name AS ArtistName, COUNT(pt.playlist_id) AS PopularityCount " +
                      "FROM Album a " +
                      "JOIN Artist ar ON a.artist_id = ar.artist_id " +
                      "LEFT JOIN Track t ON t.album_id = a.album_id " +
                      "LEFT JOIN PlaylistTrack pt ON pt.track_id = t.track_id " +
                      "GROUP BY a.album_id, a.Title, ar.artist_id, ar.Name " +
                      "ORDER BY PopularityCount DESC", nativeQuery = true)
       List<Album> findPopularAlbums();

    //AtoZ albums
       @Query(
    value = "SELECT a.album_id, a.Title, ar.artist_id, ar.Name AS ArtistName, " +
            "(SELECT COUNT(*) FROM Track t WHERE t.album_id = a.album_id) AS trackCount " +
            "FROM Album a " +
            "JOIN Artist ar ON a.artist_id = ar.artist_id " +
            "ORDER BY a.Title ASC",
       nativeQuery = true
   )
   List<Album> findAllAlbumsAtoZ();

    
    /* 
    // Native query to get popular albums (by total playlist adds)
    @Query(value = "SELECT a.album_id, a.title, ar.name AS artist_name, SUM(pt.playlist_id) AS popularity " +
                   "FROM Album a " +
                   "JOIN Artist ar ON a.artist_id = ar.artist_id " +
                   "LEFT JOIN Track t ON a.album_id = t.album_id " +
                   "LEFT JOIN Playlist_Track pt ON t.track_id = pt.track_id " +
                   "GROUP BY a.album_id, a.title, ar.name " +
                   "ORDER BY popularity DESC", nativeQuery = true)
    List<Object[]> findPopularAlbums();
    */
    

}
