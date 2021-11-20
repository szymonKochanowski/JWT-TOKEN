package com.nullpointerexception.nullpointerexception.restapi.service;

import com.nullpointerexception.nullpointerexception.restapi.model.Role;
import com.nullpointerexception.nullpointerexception.restapi.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    public Role addNewRole(Role role) {
      return roleRepository.save(role);
    }

    public Role getRoleByRoleName(String roleName) {
        return roleRepository.findRoleByRoleName(roleName);
    }
}
