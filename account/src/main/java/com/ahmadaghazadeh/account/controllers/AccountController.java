package com.ahmadaghazadeh.account.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountController {

    @GetMapping("/isOnline/{name}")
    public ResponseEntity<String> getOnline(@PathVariable("name")String name ) {
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body("Welcome, You are online and application port is {this.environment.getProperty(local.server.port)}");
    }
}
