package com.acme.apitutorial.controller;

import com.acme.apitutorial.repository.TutorialRepository;
import com.acme.apitutorial.security.jwt.AuthEntryPointJwt;
import com.acme.apitutorial.security.jwt.JwtUtils;
import com.acme.apitutorial.security.services.UserDetailsImpl;
import com.acme.apitutorial.security.services.UserDetailsServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = TutorialController.class)
@AutoConfigureMockMvc
public class TutorialControllerTest {

    @MockBean
    UserDetailsImpl userService;

    @MockBean
    UserDetailsServiceImpl userDetailsService;

    @MockBean
    AuthEntryPointJwt authEntryPointJwt;

    @MockBean
    JwtUtils jwtUtils;

    @MockBean
    TutorialRepository tutorialRepository;

    @Autowired
    TutorialController tutorialController;

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {

    }

    @Test
    public void contextLoads() {
        Assertions.assertThat(tutorialController).isNotNull();
    }

    @Test
    void registerUser() {

    }
}
