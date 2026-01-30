package com.eazybytes.jensenstore.controller;

import com.eazybytes.jensenstore.dto.ProfileResponseDto;
import com.eazybytes.jensenstore.service.IProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/profile")
public class ProfileController {

    private final IProfileService iProfileService;

    @GetMapping
    public ResponseEntity<ProfileResponseDto> getProfile(){
        ProfileResponseDto profile = iProfileService.getProfile();
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }
}
