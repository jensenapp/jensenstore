package com.eazybytes.jensenstore.controller;

import com.eazybytes.jensenstore.dto.ContactRequestDto;
import com.eazybytes.jensenstore.service.IContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/contacts")
public class ContactController {
    private final IContactService iContactService;

    @PostMapping
    public String saveContact(@RequestBody ContactRequestDto contactRequestDto){
        boolean isSaved = iContactService.saveContact(contactRequestDto);
        if (isSaved) {
            return "Request processed successfully";
        }else {
            return "An error occurred. Please try again or contact Dev team";
        }
    }

}
