package com.nullpointerexception.nullpointerexception.restapi.controller;

import com.nullpointerexception.nullpointerexception.restapi.model.Role;
import com.nullpointerexception.nullpointerexception.restapi.model.User;
import com.nullpointerexception.nullpointerexception.restapi.service.RoleService;
import com.nullpointerexception.nullpointerexception.restapi.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RestController
@Transactional
@Slf4j
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @PostMapping("/user/add")
    public ResponseEntity<User> addNewUser(@RequestBody User user) {
        log.info("Add new user with name: " + user.getUsername());
        return ResponseEntity.ok(userService.addNewUser(user));
    }

    @GetMapping("/user/get")
    public ResponseEntity<Optional<User>> getUser(@RequestParam String username) {
        log.info("Start to get user with username" + username);
        return ResponseEntity.ok(userService.findUserByUsername(username));
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        log.info("Start to get all users.");
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PutMapping("/user/addRole")
    public ResponseEntity<Void> addRoleToUser(@RequestParam String username, @RequestParam String roleName) {
        log.info("Start to add role for users:" + username + " and role: " + roleName);
        Optional<User> user = userService.findUserByUsername(username);
        Role role = roleService.getRoleByRoleName(roleName);
        log.info("End to add role for users:" + username + " and role: " + roleName);

        return new ResponseEntity(HttpStatus.OK);
    }
}
