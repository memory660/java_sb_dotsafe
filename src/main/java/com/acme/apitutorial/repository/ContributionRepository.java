package com.acme.apitutorial.repository;

import com.acme.apitutorial.model.Contribution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContributionRepository extends JpaRepository<Contribution, Long> {
    List<Contribution> findAll();

    List<Contribution> findByUserId(Long Id);
}


