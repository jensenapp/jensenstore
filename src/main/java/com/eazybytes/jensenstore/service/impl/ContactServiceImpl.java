package com.eazybytes.jensenstore.service.impl;

import com.eazybytes.jensenstore.dto.ContactRequestDto;
import com.eazybytes.jensenstore.entity.Contact;
import com.eazybytes.jensenstore.repository.ContactRepository;
import com.eazybytes.jensenstore.service.IContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements IContactService {

    private final ContactRepository contactRepository;


    @Override
    public boolean saveContact(ContactRequestDto contactRequestDto) {

        try {
        Contact contact = mapToContact(contactRequestDto);
        contact.setCreatedAt(Instant.now());
        contact.setCreatedBy(contactRequestDto.getName());
            contactRepository.save(contact);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
           return false;
        }
    }

    private Contact mapToContact(ContactRequestDto contactRequestDto){
        Contact contact = new Contact();
        BeanUtils.copyProperties(contactRequestDto,contact);
        return contact;
    }
}
