package com.revature.Revplay.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.revature.Revplay.entity.User;
import com.revature.Revplay.entity.UserRole;
import com.revature.Revplay.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // GET ALL
    @GetMapping("/all")
    public List<User> getUsers() {
        return userService.findAll();
    }

    // GET BY ID
    @GetMapping("/{id}")
    public User getById(@PathVariable Long id) {
        return userService.findById(id);
    }

    // CREATE
    @PostMapping("/add")
    public User addUser(@RequestBody User user) {
        return userService.registerUser(user);
    }

    // UPDATE
    @PutMapping("/update/{id}")
    public User updateUser(@PathVariable Long id,
                           @RequestBody User updatedUser) {

        User existing = userService.findById(id);

        existing.setUserName(updatedUser.getUserName());
        existing.setEmail(updatedUser.getEmail());
        existing.setBio(updatedUser.getBio());
        existing.setProfileImage(updatedUser.getProfileImage());
        existing.setRole(updatedUser.getRole());

        return userService.registerUser(existing);
    }

    // DELETE
    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return "User deleted successfully";
    }

    @GetMapping("/test")
    public String testAddUser() {

        User user = new User();
        user.setUserName("arijit");
        user.setEmail("test123994@gmail.com");
        user.setUserPassword("124234566");
        user.setRole(UserRole.ARTIST);
        user.setSecurityQuestion("Pet name?");
        user.setSecurityAnswer("amazon");
        user.setBio("test bio 03");
        user.setProfileImage("profile link here 93");


        userService.registerUser(user);

        return "User inserted successfully!";
    }
}