package com.example.bankapp.controllers;

import com.example.bankapp.dtos.auth.AuthResponse;
import com.example.bankapp.dtos.auth.LoginRequest;
import com.example.bankapp.dtos.auth.RegisterRequest;
import com.example.bankapp.entities.AppRole;
import com.example.bankapp.entities.AppUser;
import com.example.bankapp.repositories.AppRoleRepository;
import com.example.bankapp.repositories.AppUserRepository;
import com.example.bankapp.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AppUserRepository userRepository;
    private final AppRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest request) {
        // Vérifier si l'utilisateur existe déjà
        if (userRepository.existsByUsername(request.username())) {
            throw new RuntimeException("Ce nom d'utilisateur est déjà pris");
        }

        // Récupérer ou créer le rôle par défaut "USER"
        AppRole userRole = roleRepository.findByRoleName("USER");
        if (userRole == null) {
            userRole = new AppRole();
            userRole.setRoleName("USER");
            roleRepository.save(userRole);
        }

        AppUser user = new AppUser();
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRoles(List.of(userRole));

        userRepository.save(user);
        
        String jwtToken = jwtService.generateToken(user);
        return new AuthResponse(jwtToken);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        // Authentifie l'utilisateur via Spring Security
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        AppUser user = userRepository.findByUsername(request.username())
                .orElseThrow();
                
        String jwtToken = jwtService.generateToken(user);
        return new AuthResponse(jwtToken);
    }
}