package com.tunes.SpringTunes.controller;

import com.tunes.SpringTunes.model.Users;
import com.tunes.SpringTunes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/getUser")
    public Users getUser(@RequestParam Long id) {
        return userService.getUser(id);
    }

    @PostMapping("/addUser")
    public String addUser(@RequestBody Users users) {
        return userService.addUser(users);
    }

    @DeleteMapping("/deleteUser")
    public String deleteUser(@RequestParam Long id) {
        return userService.deleteUser(id);
    }

    @PutMapping("/updateUser")
    public String updateUser(@RequestBody Users users) {
        return userService.updateUser(users);
    }

    @GetMapping("/searchUser")
    public Users searchUserByUsername(@RequestParam String username) {
        return userService.searchByUsername(username);
    }

    @GetMapping("/getAll")
    public List<Users> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/searchByName")
    public List<Users> searchByName(@RequestParam String name) {
        return userService.searchByName(name);
    }
}
