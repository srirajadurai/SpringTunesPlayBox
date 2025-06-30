package com.tunes.SpringTunes.repository;

import com.tunes.SpringTunes.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    Users findByUsername(String username);
    List<Users> findByNameContainingIgnoreCase(String name);

}
