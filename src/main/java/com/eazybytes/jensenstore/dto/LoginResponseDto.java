package com.eazybytes.jensenstore.dto;

public record LoginResponseDto(String message, UserDto user, String jwtToken) {
}
