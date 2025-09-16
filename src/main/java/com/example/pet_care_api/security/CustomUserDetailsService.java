package com.example.pet_care_api.security;

import com.example.pet_care_api.models.PetClinic;
import com.example.pet_care_api.models.PetOwner;
import com.example.pet_care_api.repositories.PetClinicRepository;
import com.example.pet_care_api.repositories.PetOwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private PetOwnerRepository petOwnerRepo;

    @Autowired
    private PetClinicRepository petClinicRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        PetOwner owner = petOwnerRepo.findByUsername(username);
        if (owner != null) {
            return new User(owner.getUsername(), owner.getPassword(),
                    List.of(new SimpleGrantedAuthority(owner.getRole().name())));
        }

        PetClinic clinic = petClinicRepo.findByUsername(username);
        if (clinic != null) {
            return new User(clinic.getUsername(), clinic.getPassword(),
                    List.of(new SimpleGrantedAuthority(clinic.getRole().name())));
        }

        throw new UsernameNotFoundException("User not found: " + username);
    }
}
