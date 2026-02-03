package com.eazybytes.jensenstore.controller;

import com.eazybytes.jensenstore.dto.ProfileRequestDto;
import com.eazybytes.jensenstore.dto.ProfileResponseDto;
import com.eazybytes.jensenstore.service.IProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/profile")
public class ProfileController {

    private final IProfileService iProfileService;


    @PutMapping
    public  ResponseEntity<ProfileResponseDto> updateProfile(@RequestBody @Valid ProfileRequestDto profileRequestDto){
        ProfileResponseDto profileResponseDto = iProfileService.updateProfile(profileRequestDto);
        return new ResponseEntity<>(profileResponseDto,HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<ProfileResponseDto> getProfile(){
        ProfileResponseDto profile = iProfileService.getProfile();
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }
}
