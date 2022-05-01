package com.acme.apitutorial.controller;

import com.acme.apitutorial.model.Contribution;
import com.acme.apitutorial.model.User;
import com.acme.apitutorial.repository.ContributionRepository;
import com.acme.apitutorial.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserRepository userRepository;
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            List<User> users = new ArrayList<>();
            userRepository.findAll().forEach(users::add);

            if (users.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
