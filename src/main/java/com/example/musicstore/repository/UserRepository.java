package com.example.musicstore.repository;

import com.example.musicstore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /*User findByEmail(String email);*/
    Optional<User> findByEmail(String email);
    
   /*  User findByEmailAndPassword(String email, String password); */ 

}
