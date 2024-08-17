package com.example.gstbilling.controller;

import com.example.gstbilling.model.User;
import com.example.gstbilling.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public User signUp(@RequestParam String username,
                       @RequestParam String password,
                       @RequestParam String role) {
        return userService.signUp(username, password, role);
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password) {
        return userService.login(username, password);
    }
}
