package com.acme.apitutorial.repository;

import com.acme.apitutorial.model.Contribution;
import com.acme.apitutorial.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findAll();

    Optional<Project> findById(Long Id);
}

