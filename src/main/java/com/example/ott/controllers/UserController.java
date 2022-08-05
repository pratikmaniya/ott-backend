package com.example.ott.controllers;

import com.example.ott.CustomizedResponse;
import com.example.ott.models.UserModel;
import com.example.ott.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @CrossOrigin(origins={"http://localhost:3000", "https://ott-244.herokuapp.com/"})
    @PostMapping(value = "/register", consumes = {
            MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseEntity addUser(@RequestBody UserModel user) {
        try {
            CustomizedResponse customizedResponse = new CustomizedResponse("User created successfully", Collections.singletonList(userService.insertUser(user)));
            return new ResponseEntity(customizedResponse, HttpStatus.OK);
        }catch (Exception e){
            CustomizedResponse customizedResponse = new CustomizedResponse(e.getMessage(), null);
            return new ResponseEntity(customizedResponse, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @CrossOrigin(origins={"http://localhost:3000", "https://ott-244.herokuapp.com/"})
    @GetMapping("/users/{id}")
    public ResponseEntity getUser(@PathVariable("id") String id){
        CustomizedResponse customizedResponse = null;
        try {
            customizedResponse = new CustomizedResponse(" User with id " + id , Collections.singletonList(userService.getUser(id)));
        } catch (Exception e) {
            customizedResponse = new CustomizedResponse(e.getMessage(), null);
            return new ResponseEntity(customizedResponse, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(customizedResponse, HttpStatus.OK);
    }
}