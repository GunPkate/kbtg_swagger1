package com.example.swagger.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

    @RestController
    @RequestMapping("/users")
public class UserController {
        @GetMapping
        public  ResponseEntity<List<String>> listUser(){
            return new ResponseEntity<>( List.of("aa","bb"), HttpStatus.OK);
        }
}
