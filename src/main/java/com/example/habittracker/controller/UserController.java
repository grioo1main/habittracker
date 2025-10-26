package com.example.habittracker.controller;

import com.example.habittracker.model.User;
import com.example.habittracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import org.springframework.web.bind.annotation.RequestParam;


@RestController  // ← ОБЯЗАТЕЛЬНО добавьте эту аннотацию!
public class UserController {
    
    private final UserService userService;
    
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsersWithHabits();
    }
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        
        return userService.getUserById(id).orElseThrow();
    }
    

}
