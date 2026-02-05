package com.eazybytes.jensenstore.controller;

import com.eazybytes.jensenstore.constants.ApplicationConstants;
import com.eazybytes.jensenstore.dto.ContactRequestDto;
import com.eazybytes.jensenstore.dto.ContactResponseDto;
import com.eazybytes.jensenstore.dto.ResponseDto;
import com.eazybytes.jensenstore.service.IContactService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/contacts")
@RequiredArgsConstructor
public class ContactController {

    private final IContactService iContactService;

    @PostMapping
    public ResponseEntity<String> saveContact(
            @Valid @RequestBody ContactRequestDto contactRequestDto) {
        iContactService.saveContact(contactRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Request processed successfully");
    }

}
