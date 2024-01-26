package com.stockz.portfolio.controller;


import com.stockz.portfolio.entity.User;
import com.stockz.portfolio.repository.UserRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/user")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @GetMapping("/")
    public ResponseEntity<List<User>> getUsers(){
        try{
            List<User> users = userRepository.findAll();
            return new ResponseEntity<>(users,HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/")
    public ResponseEntity<User> postUser(@RequestBody User obj){
        try {
            User user = userRepository.save(obj);
            return new ResponseEntity<>(user,HttpStatus.CREATED);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
        }
    }
}
