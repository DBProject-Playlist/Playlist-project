package com.example.musicstore.service;

import com.example.musicstore.entity.Album;
import com.example.musicstore.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
//import java.util.stream.Collectors;
//import org.springframework.data.domain.Sort;

@Service
public class AlbumService {
    
    @Autowired
    private AlbumRepository albumRepository;
    
    public List<Album> getAllAlbums() {
        return albumRepository.findAll();
    }

    //public List<Album> getPopularAlbums() {
        // Popular albums = albums with most tracks
        //return albumRepository.findAllByOrderByTracksDesc();
   // }

   //public List<Album> getPopularAlbums() {
    //return albumRepository.findAllByOrderByPopularityDesc();
//}

    
    /*public List<Album> getAlbumsSortedByName() {
        return albumRepository.findAllByOrderByTitleAsc();
    }

    public List<Album> getPopularAlbums() {
        List<Object[]> results = albumRepository.findPopularAlbums();
        // Extract only Album entity from Object[]
        return results.stream()
                .map(row -> (Album) row[0])
                .collect(Collectors.toList());
    }

    // For A–Z sorting
    public List<Album> getAlbumsSortedByName() {
        return albumRepository.findAll()
                .stream()
                .sorted((a1, a2) -> a1.getTitle().compareToIgnoreCase(a2.getTitle()))
                .collect(Collectors.toList());
    } 

    // Sort alphabetically
    public List<Album> getAllAlbumsSortedByTitle() {
        return albumRepository.findAll(Sort.by(Sort.Direction.ASC, "title"));
    }

    // Sort by popularity (number of playlist adds)
    public List<Album> getAllAlbumsSortedByPopularity() {
        List<Album> albums = albumRepository.findAll();
        albums.sort((a1, a2) -> Integer.compare(
            a2.getTracks().stream().mapToInt(t -> t.getPlaylistTracks().size()).sum(),
            a1.getTracks().stream().mapToInt(t -> t.getPlaylistTracks().size()).sum()
        ));
        return albums;
    }*/

    public List<Album> getAlbumsAtoZ() {
        return albumRepository.findAllAlbumsAtoZ();
    }

    public List<Album> getPopularAlbums() {
        return albumRepository.findPopularAlbums();
    }


}