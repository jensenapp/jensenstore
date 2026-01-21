package com.eazybytes.jensenstore.service.impl;

import com.eazybytes.jensenstore.dto.ContactRequestDto;
import com.eazybytes.jensenstore.entity.Contact;
import com.eazybytes.jensenstore.repository.ContactRepository;
import com.eazybytes.jensenstore.service.IContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements IContactService {

    private final ContactRepository contactRepository;


    @Override
    public boolean saveContact(ContactRequestDto contactRequestDto) {
        Contact contact = mapToContact(contactRequestDto);
        contactRepository.save(contact);
        return true;
//        throw new RuntimeException("Oops, something bad happened");
    }

    private Contact mapToContact(ContactRequestDto contactRequestDto){
        Contact contact = new Contact();
        BeanUtils.copyProperties(contactRequestDto,contact);
        return contact;
    }
}
