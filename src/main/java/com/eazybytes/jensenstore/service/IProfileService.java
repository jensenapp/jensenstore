package com.eazybytes.jensenstore.service;

import com.eazybytes.jensenstore.dto.ProfileRequestDto;
import com.eazybytes.jensenstore.dto.ProfileResponseDto;

public interface IProfileService {
    ProfileResponseDto getProfile();
    ProfileResponseDto updateProfile(ProfileRequestDto profileRequestDto);
}
