package com.acme.apitutorial.controller;

import com.acme.apitutorial.model.ERole;
import com.acme.apitutorial.model.Role;
import com.acme.apitutorial.model.User;
import com.acme.apitutorial.payload.request.SignupRequest;
import com.acme.apitutorial.payload.response.MessageResponse;
import com.acme.apitutorial.repository.RoleRepository;
import com.acme.apitutorial.repository.UserRepository;
import com.acme.apitutorial.security.jwt.AuthEntryPointJwt;
import com.acme.apitutorial.security.jwt.JwtUtils;
import com.acme.apitutorial.security.services.UserDetailsServiceImpl;
import com.acme.apitutorial.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvc;
import com.acme.apitutorial.model.User;
import com.acme.apitutorial.payload.request.SignupRequest;
import com.acme.apitutorial.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashSet;
import java.util.Set;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest(controllers = AuthController.class)
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    AuthController authController;

    @Autowired
    MockMvc mvc;

    @MockBean
    AuthService authService;

    @MockBean
    UserDetailsServiceImpl userDetailsService;

    @MockBean
    AuthEntryPointJwt authEntryPointJwt;

    @MockBean
    JwtUtils jwtUtils;

    @MockBean
    UserRepository userRepository;

    @MockBean
    RoleRepository roleRepository;

    SignupRequest signupRequest;
    User user;

    private static final String API = "/api/auth/signup";
    private static final Long ID = 1L;
    private static final String USERNAME = "test1";
    private static final String EMAIL = "test1@test.com";
    private static final String PASSWORD = "111111";
    private static final String ENCRYPTED_PASSWORD = "$2a$10$GjrUCAzKSbqsFx38DMpAR.IoL5alFtW.oGjjMwMjVUvI1CFRef8OC";
    private static final String ROLE = "ROLE_USER";

    @BeforeEach
    void setUp() {

        Role role = new Role();
        role.setName(ERole.ROLE_USER);

        user = User.builder()
                .id(ID).username(USERNAME)
                .email(EMAIL).password(ENCRYPTED_PASSWORD).roles(new HashSet<>()).build();

        signupRequest = SignupRequest.builder()
                .username(USERNAME).email(EMAIL).password(PASSWORD).build();
    }

    @Test
    public void contextLoads() {
        Assertions.assertThat(authController).isNotNull();
    }

    @Test
    void registerUser() {
/*
        String content = "User registered successfully!";
        when(authService.createUser(signupRequest)).thenReturn(user);

        when(userRepository.existsByUsername(any())).thenReturn(false);
        when(userRepository.existsByEmail(any())).thenReturn(false);

        //act
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(API)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content);

        //assert
        mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").value(ID))
                .andExpect(new MessageResponse("User registered successfully!"));
 */
    }
}
