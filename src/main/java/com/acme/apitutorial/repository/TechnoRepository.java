package com.acme.apitutorial.repository;

import com.acme.apitutorial.model.Contribution;
import com.acme.apitutorial.model.Techno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TechnoRepository extends JpaRepository<Techno, Long> {
    List<Techno> findAll();

}

