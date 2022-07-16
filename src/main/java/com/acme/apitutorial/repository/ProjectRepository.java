package com.acme.apitutorial.repository;

import com.acme.apitutorial.model.Contribution;
import com.acme.apitutorial.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findAll();

    Optional<Project> findById(Long Id);
}

