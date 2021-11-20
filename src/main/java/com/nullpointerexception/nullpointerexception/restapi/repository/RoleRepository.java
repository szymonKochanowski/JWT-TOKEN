package com.nullpointerexception.nullpointerexception.restapi.repository;

import com.nullpointerexception.nullpointerexception.restapi.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

   Role findRoleByRoleName(String roleName);

}
