package com.example.musicstore.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.musicstore.entity.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}

