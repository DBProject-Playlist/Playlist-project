package com.example.musicstore.controller;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.data.domain.Sort;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RequestParam;

import com.example.musicstore.entity.Album;
import com.example.musicstore.entity.Track;
import com.example.musicstore.repository.AlbumRepository;
import com.example.musicstore.repository.TrackRepository;
import com.example.musicstore.service.AlbumService;

import java.util.List;

@Controller
public class AlbumController {

    private final AlbumRepository albumRepository;
    private final TrackRepository trackRepository;

    
    private final AlbumService albumService;



    public AlbumController(AlbumRepository albumRepository, TrackRepository trackRepository, AlbumService albumService) {
        this.albumRepository = albumRepository;
        this.trackRepository = trackRepository;
        this.albumService = albumService;
    }
    
    
   /* @GetMapping
    public String listAlbums(Model model) {
        List<Album> albums = albumService.getAllAlbums();
        model.addAttribute("albums", albums);
        return "albums";
    } */

   /* 
   @GetMapping
    public String listAlbums(@RequestParam(name = "sort", defaultValue = "popular") String sort, Model model) {
        List<Album> albums;
        
        if ("az".equals(sort)) {
            albums = albumService.getAlbumsSortedByName();
        } else {
            albums = albumService.getPopularAlbums();
        }
        
        model.addAttribute("albums", albums);
        model.addAttribute("currentSort", sort);
        return "albums";
    }
    
    @GetMapping
    public String listAlbums(@RequestParam(name = "sort", defaultValue = "popular") String sort, Model model) {
        List<Album> albums;

        if ("az".equals(sort)) {
             albums = albumService.getAlbumsSortedByName();
        } else {
             albums = albumService.getPopularAlbums(); // Popular albums
    }

        model.addAttribute("albums", albums);
        model.addAttribute("currentSort", sort);
        return "albums"; 
}
    @GetMapping("/albums")
    public String albums(@RequestParam(name = "sort", defaultValue = "popular") String sort, Model model) {
        List<Object[]> albums;
        if ("az".equals(sort)) {
            albums = albumService.getAlbumsAtoZ();
        } else {
            albums = albumService.getPopularAlbums();
        }
        model.addAttribute("albums", albums);
        model.addAttribute("currentSort", sort);
        return "albums";
    }*/

    @GetMapping("/albumsLists")
    public String albums(Model model) {
        List<Album> albums = albumService.getAlbumsAtoZ();
        model.addAttribute("albums", albums);
        model.addAttribute("currentSort", "az"); // mark A-Z as active
        return "albums";
    }

    // Popular Albums
    @GetMapping("/albumsPopular")
    public String albumsPopular(Model model) {
        List<Album> albums = albumService.getPopularAlbums();
        model.addAttribute("albums", albums);
        model.addAttribute("currentSort", "popular");
        return "albums";
    }

    

    @GetMapping("/album/{id}")
    public String showAlbumDetails(@PathVariable Long id, Model model) {

        Album album = albumRepository.findById(id).orElse(null);
        if (album == null) {
            return "redirect:/artists";
        }

        List<Track> tracks = trackRepository.findByAlbum(album);

        model.addAttribute("album", album);
        model.addAttribute("tracks", tracks);

        return "albumDetails"; 
    }


/* 
    @GetMapping("/albumLists")
    public String listAlbums(@RequestParam(defaultValue = "name") String sort, Model model) {
        List<Album> albums;

        switch (sort) {
            case "popular":
                albums = albumRepository.findAlbumsByPopularity();
                break;
            case "artist":
                albums = albumRepository.findAll(Sort.by(Sort.Direction.ASC, "artist.name"));
                break;
            case "name":
            default:
                albums = albumRepository.findAll(Sort.by(Sort.Direction.ASC, "title"));
                break;
        }

        model.addAttribute("albums", albums);
        model.addAttribute("selectedSort", sort);
        return "albums";
}
*/

}


