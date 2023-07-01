package com.ansh.user.service.controllers;

import com.ansh.user.service.entities.User;
import com.ansh.user.service.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    //create
    @PostMapping //will directly create user on calling POST Method
    public ResponseEntity<User> createUser(@RequestBody User user){
        User user1 = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user1);
    }

    //single user get
    @GetMapping("/{userId}") // will get user by userId
    public ResponseEntity<User> getSingleUser(@PathVariable(value = "userId") String userId){
        User user = userService.getUser(userId);
        return ResponseEntity.ok(user);
    }

    //multiple user get
    @GetMapping //will get allUsers by GET Method
    public ResponseEntity<List<User>> getAllUser(){
        List<User> allUser = userService.getAllUser();
        return ResponseEntity.ok(allUser);
    }


}
