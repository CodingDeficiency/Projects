package com.adams.portfolio.controller;

import com.adams.portfolio.model.Profile;
import com.adams.portfolio.repository.ProfileRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profile")
@CrossOrigin(origins = "http://localhost:4200")
public class ProfileController {

    private final ProfileRepository profileRepository;

    public ProfileController(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @GetMapping
    public ResponseEntity<Profile> getProfile() {
        List<Profile> all = profileRepository.findAll();
        if (all.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(all.get(0));
    }
}