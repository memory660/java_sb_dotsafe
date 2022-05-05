package com.acme.apitutorial.repository;

import com.acme.apitutorial.model.ERole;
import com.acme.apitutorial.model.Role;
import com.acme.apitutorial.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
