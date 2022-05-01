package com.acme.apitutorial.repository;

import com.acme.apitutorial.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAll();
}
