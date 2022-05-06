package com.acme.apitutorial.controller;

import com.acme.apitutorial.dto.ContributionDto;
import com.acme.apitutorial.model.Contribution;
import com.acme.apitutorial.model.Project;
import com.acme.apitutorial.model.Techno;
import com.acme.apitutorial.model.User;
import com.acme.apitutorial.repository.ContributionRepository;
import com.acme.apitutorial.repository.ProjectRepository;
import com.acme.apitutorial.repository.TechnoRepository;
import com.acme.apitutorial.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class ContributionController {

    @Autowired
    ContributionRepository contributionRepository;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    TechnoRepository technoRepository;

    @Autowired
    UserRepository userRepository;

    @PreAuthorize("hasAuthority('ROLE_USER')")
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

    @PreAuthorize("hasAuthority('ROLE_USER')")
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

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PostMapping("/contributions")
    public ResponseEntity<Contribution> registerContribution(@Valid @RequestBody ContributionDto registerContributionDto) {
        try {
            Optional<Project> project = projectRepository.findById(registerContributionDto.getProjectId());
            Optional<Techno> techno = technoRepository.findById(registerContributionDto.getTechnoId());
            Optional<User> user = userRepository.findById(registerContributionDto.getUserId());

            Contribution contributionConverted = registerContributionDto.toContribution(project.get(), techno.get(), user.get());
            if (contributionConverted != null) {
                Contribution _contribution = contributionRepository.save(contributionConverted);
                return new ResponseEntity<>(_contribution, HttpStatus.CREATED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
