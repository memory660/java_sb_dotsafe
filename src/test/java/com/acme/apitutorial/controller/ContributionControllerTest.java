package com.acme.apitutorial.controller;

import com.acme.apitutorial.repository.ContributionRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = ContributionController.class)
@AutoConfigureMockMvc
public class ContributionControllerTest {

    @MockBean
    ContributionRepository contributionRepository;

    @Autowired
    ContributionController contributionController;

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {

    }

    @Test
    public void contextLoads() {
        Assertions.assertThat(contributionController).isNotNull();
    }

    @Test
    void registerUser() {

    }
}
