package com.tunes.SpringTunes.controller;

import com.tunes.SpringTunes.model.Users;
import com.tunes.SpringTunes.repository.UserRepository;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;

import java.io.IOException;

import org.springframework.http.ResponseEntity;

@Controller
public class WelcomeController {

    private final ResourceLoader resourceLoader;
    private final UserRepository userRepository;

    public WelcomeController(ResourceLoader resourceLoader, UserRepository usersRepository) {
        this.resourceLoader = resourceLoader;
        this.userRepository = usersRepository;
    }

    @GetMapping("/login")
    public ResponseEntity<Resource> showLoginPage() {
        Resource resource = resourceLoader.getResource("classpath:static/login.html");
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_HTML)
                .body(resource);
    }

    @GetMapping("/")
    public String home() {
        return "Welcome! <a href='/login'>Login</a>";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "Error: Access Denied";
    }

    @GetMapping("/admin/home/")
    public String adminHome(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Users user = userRepository.findByUsername(username);
        model.addAttribute("name", user.getName());
        return "admin-home";
    }

    @GetMapping("/user/home/")
    public String userHome(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Users user = userRepository.findByUsername(username);
        model.addAttribute("name", user.getName());
        return "user-home";
    }
}