package com.nullpointerexception.nullpointerexception.restapi.controller;

import com.nullpointerexception.nullpointerexception.restapi.configuration.LogiCredentials;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class LoginController {

    @PostMapping("/login") //to jest metoda ktora umozliwia nam jedynie strzelanie do metody, nie musi ona nic zwracac poniewaz, za uwierzytelnienie odpowiada filtr
    public ResponseEntity<Void> login(@RequestBody LogiCredentials logiCredentials) {
        log.info("Start logging to app user: " + logiCredentials.getUsername());
        return new ResponseEntity(HttpStatus.OK);
    }
}
