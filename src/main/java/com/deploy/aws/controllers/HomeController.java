package com.deploy.aws.controllers;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class HomeController{

    @GetMapping(path = "/")
    public ResponseEntity<String> home(){
        return ResponseEntity.ok("Home Page is Running Successfully");
    }
}

