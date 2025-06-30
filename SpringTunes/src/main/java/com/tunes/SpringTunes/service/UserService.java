package com.tunes.SpringTunes.service;

import com.tunes.SpringTunes.model.Users;
import com.tunes.SpringTunes.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Users getUser(Long id) {
        Optional<Users> user = userRepository.findById(id);
        return user.orElse(null);
    }

    public String addUser(Users user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            return "User with this username already exists.";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User added successfully";
    }

    public String deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            return "User not found.";
        }
        userRepository.deleteById(id);
        return "User deleted successfully";
    }

    public String updateUser(Users updatedUser) {
        Optional<Users> existingUser = userRepository.findById(updatedUser.getId());
        if (existingUser.isEmpty()) {
            return "User not found.";
        }

        Users user = existingUser.get();
        user.setUsername(updatedUser.getUsername());
        user.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        user.setRole(updatedUser.getRole());
        user.setName(updatedUser.getName());

        userRepository.save(user);
        return "User updated successfully";
    }

    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    public Users searchByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<Users> searchByName(String name){
        return userRepository.findByNameContainingIgnoreCase(name);
    }
}
