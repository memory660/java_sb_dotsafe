package com.acme.apitutorial.repository;

import com.acme.apitutorial.model.Contribution;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContributionRepository extends JpaRepository<Contribution, Long> {
    List<Contribution> findAll();

    List<Contribution> findByUserId(Long Id);
}


