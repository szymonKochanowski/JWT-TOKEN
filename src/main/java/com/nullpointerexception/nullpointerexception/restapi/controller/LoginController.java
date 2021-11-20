package com.nullpointerexception.nullpointerexception.restapi.controller;

import com.nullpointerexception.nullpointerexception.restapi.model.User;
import com.nullpointerexception.nullpointerexception.restapi.service.RoleService;
import com.nullpointerexception.nullpointerexception.restapi.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@Slf4j
public class LoginController {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody User user) {
        log.info("Start logging to app user: " + user.getUsername());
        Optional<User> userFromDB = userService.findUserByUsername(user.getUsername());

        if (userFromDB.isEmpty() || wrongPassword(userFromDB, user))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        log.info("Logged user with username: " + user.getUsername());
        return new ResponseEntity(HttpStatus.OK);
    }

    private boolean wrongPassword(Optional<User> userFromDB, User user) {
        return !userFromDB.get().getPassword().equals(user.getPassword());
    }
}
