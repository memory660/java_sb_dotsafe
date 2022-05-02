package com.acme.apitutorial.controller;

import com.acme.apitutorial.dto.ContributionDto;
import com.acme.apitutorial.model.Contribution;
import com.acme.apitutorial.repository.ContributionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class ContributionController {

    @Autowired
    ContributionRepository contributionRepository;

    @GetMapping("/contributions")
    public ResponseEntity<List<Contribution>> getAllContributions() {
        try {
            List<Contribution> contributions = new ArrayList<>();
            contributionRepository.findAll().forEach(contributions::add);

            if (contributions.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(contributions, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users/{id}/contributions")
    public ResponseEntity<List<Contribution>> getContributionsByUserId(@PathVariable("id") long id) {
        try {
            List<Contribution> contributions = new ArrayList<>();
            contributionRepository.findByUserId(id).forEach(contributions::add);

            if (contributions.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(contributions, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/contributions")
    public ResponseEntity<Contribution> registerContribution(@Valid @RequestBody ContributionDto registerContributionDto) {
        try {
            Contribution _contribution = contributionRepository
                    .save(registerContributionDto.toContribution());
            return new ResponseEntity<>(_contribution, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
