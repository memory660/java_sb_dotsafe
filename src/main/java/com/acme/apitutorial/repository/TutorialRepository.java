package com.acme.apitutorial.repository;

import java.util.List;
import java.util.Optional;

import com.acme.apitutorial.model.Tutorial;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TutorialRepository extends JpaRepository<Tutorial, Long> {
    List<Tutorial> findByPublished(boolean published);
    List<Tutorial> findByTitleContaining(String title);
    //
    List<Tutorial> findByLevel(Integer level);
    Optional<Tutorial> findByDescription(String description);
    List<Tutorial> findByPublishedOrderByLevelDesc(boolean published);
}

// save(), findOne(), findById(), findAll(), count(), delete(), deleteById()…