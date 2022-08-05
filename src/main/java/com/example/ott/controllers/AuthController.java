package com.example.ott.controllers;

import com.example.ott.CustomizedResponse;
import com.example.ott.models.UserModel;
import com.example.ott.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;

    @CrossOrigin(origins={"http://localhost:5000", "https://ott-244.herokuapp.com/"})
    @PostMapping(value = "/login", consumes = {
            MediaType.APPLICATION_JSON_VALUE
    })
    public ResponseEntity login(@RequestBody UserModel user){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
            CustomizedResponse customizedResponse = new CustomizedResponse("Login successful", Collections.singletonList(userService.getUserByEmail(user.getEmail()).getId()));
            return new ResponseEntity(customizedResponse, HttpStatus.OK);
        }catch (BadCredentialsException be){
            CustomizedResponse customizedResponse = new CustomizedResponse("Incorrect credential", null);
            return new ResponseEntity(customizedResponse, HttpStatus.UNAUTHORIZED);
        }catch (Exception e){
            CustomizedResponse customizedResponse = new CustomizedResponse(e.getMessage(), null);
            return new ResponseEntity(customizedResponse, HttpStatus.EXPECTATION_FAILED);
        }
    }
}
