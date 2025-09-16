package com.example.pet_care_api.controllers;

import com.example.pet_care_api.controllers.dto.request.LoginRequest;
import com.example.pet_care_api.controllers.dto.request.RegisterPetClinicRequest;
import com.example.pet_care_api.controllers.dto.request.RegisterPetOwnerRequest;
import com.example.pet_care_api.controllers.dto.response.LoginResponse;
import com.example.pet_care_api.models.PetClinic;
import com.example.pet_care_api.models.PetOwner;
import com.example.pet_care_api.models.Role;
import com.example.pet_care_api.repositories.PetClinicRepository;
import com.example.pet_care_api.repositories.PetOwnerRepository;
import com.example.pet_care_api.security.CustomUserDetailsService;
import com.example.pet_care_api.security.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtUtil jwtUtil;
    private final PetOwnerRepository petOwnerRepo;
    private final PetClinicRepository petClinicRepo;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager,
                          CustomUserDetailsService customUserDetailsService,
                          JwtUtil jwtUtil,
                          PetOwnerRepository petOwnerRepo,
                          PetClinicRepository petClinicRepo,
                          PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.customUserDetailsService = customUserDetailsService;
        this.jwtUtil = jwtUtil;
        this.petOwnerRepo = petOwnerRepo;
        this.petClinicRepo = petClinicRepo;
        this.passwordEncoder = passwordEncoder;
    }

    // 🔹 Register PetOwner
    @PostMapping("/register/owner")
    public ResponseEntity<?> registerOwner(@RequestBody RegisterPetOwnerRequest request) {
        if (petOwnerRepo.findByUsername(request.getUsername()) != null) {
            return ResponseEntity.badRequest().body("Username already exists");
        }

        PetOwner owner = new PetOwner();
        owner.setOwnerName(request.getOwnerName());
        owner.setAddress(request.getAddress());
        owner.setPhoneNumber(request.getPhoneNumber());
        owner.setUsername(request.getUsername());
        owner.setPassword(passwordEncoder.encode(request.getPassword()));
        owner.setRole(Role.ROLE_USER);

        petOwnerRepo.save(owner);
        return ResponseEntity.ok("PetOwner registered successfully");
    }

    // 🔹 Register PetClinic
    @PostMapping("/register/clinic")
    public ResponseEntity<?> registerClinic(@RequestBody RegisterPetClinicRequest request) {
        if (petClinicRepo.findByUsername(request.getUsername()) != null) {
            return ResponseEntity.badRequest().body("Username already exists");
        }

        PetClinic clinic = new PetClinic();
        clinic.setClinicName(request.getClinicName());
        clinic.setAddress(request.getAddress());
        clinic.setPhoneNumber(request.getPhoneNumber());
        clinic.setUsername(request.getUsername());
        clinic.setPassword(passwordEncoder.encode(request.getPassword()));
        clinic.setRole(Role.ROLE_ADMIN);

        petClinicRepo.save(clinic);
        return ResponseEntity.ok("PetClinic registered successfully");
    }

    // 🔹 Shared Login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }

        // Load user details
        final UserDetails userDetails = customUserDetailsService.loadUserByUsername(request.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);

        // Determine if user is a PetOwner or PetClinic
        String role;
        if (petOwnerRepo.findByUsername(request.getUsername()) != null) {
            role = Role.ROLE_USER.name();
        } else {
            role = Role.ROLE_ADMIN.name();
        }

        LoginResponse response = new LoginResponse(); // no-args constructor
        response.setUsername(request.getUsername());
        response.setRole(role);
        response.setToken(jwt);

        return ResponseEntity.ok(response);
    }

}
