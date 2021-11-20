package com.nullpointerexception.nullpointerexception.restapi.controller;

import com.nullpointerexception.nullpointerexception.restapi.model.Role;
import com.nullpointerexception.nullpointerexception.restapi.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class RoleController {

    @Autowired
    RoleService roleService;

    @PostMapping("/role/add")
    public ResponseEntity<Role> addNewRole(@RequestBody Role role) {
        log.info("Start to add new role");
        return ResponseEntity.ok(roleService.addNewRole(role));
    }

    @GetMapping("/role/get")
    public ResponseEntity<Role> getRole(@RequestParam String roleName) {
        return ResponseEntity.ok(roleService.getRoleByRoleName(roleName));
    }
}
