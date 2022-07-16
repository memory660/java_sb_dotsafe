package com.acme.apitutorial.service;

import com.acme.apitutorial.model.ERole;
import com.acme.apitutorial.model.Role;
import com.acme.apitutorial.model.User;
import com.acme.apitutorial.payload.response.MessageResponse;
import com.acme.apitutorial.repository.RoleRepository;
import com.acme.apitutorial.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.acme.apitutorial.payload.request.SignupRequest;
import java.util.HashSet;
import java.util.Set;

@Service
public class AuthService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    public Set<Role> getDeterminatedRole(Set<String> strRoles) {
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        return roles;
    }

    public User createUser(SignupRequest signUpRequest) {

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));
        Set<String> strRoles = signUpRequest.getRole();

        Set<Role> roles = getDeterminatedRole(strRoles);

        user.setRoles(roles);
        userRepository.save(user);

        return user;
    }
}
