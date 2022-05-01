package com.acme.apitutorial.repository;

import java.util.List;
import com.acme.apitutorial.model.Tutorial;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TutorialRepository extends JpaRepository<Tutorial, Long> {
    List<Tutorial> findByPublished(boolean published);
    List<Tutorial> findByTitleContaining(String title);
}

// save(), findOne(), findById(), findAll(), count(), delete(), deleteById()…