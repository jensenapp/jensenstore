package com.eazybytes.jensenstore.service.impl;

import com.eazybytes.jensenstore.constants.ApplicationConstants;
import com.eazybytes.jensenstore.dto.ContactRequestDto;
import com.eazybytes.jensenstore.dto.ContactResponseDto;
import com.eazybytes.jensenstore.entity.Contact;
import com.eazybytes.jensenstore.exception.ResourceNotFoundException;
import com.eazybytes.jensenstore.repository.ContactRepository;
import com.eazybytes.jensenstore.service.IContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements IContactService {

    private final ContactRepository contactRepository;


    @Override
    public boolean saveContact(ContactRequestDto contactRequestDto) {
        Contact contact = mapToContact(contactRequestDto);
        contact.setStatus(ApplicationConstants.OPEN_MESSAGE);
        contactRepository.save(contact);
        return true;
//        throw new RuntimeException("Oops, something bad happened");
    }

    @Override
    public List<ContactResponseDto> getAllOpenMessages() {
        List<Contact> lists = contactRepository.findByStatus(ApplicationConstants.OPEN_MESSAGE);
        return lists.stream().map(list -> mapToContactResponseDto(list)).toList();
    }

    @Override
    public void updateMessageStatus(Long contactId, String status) {
        Contact contact = contactRepository.findById(contactId).orElseThrow(()->new ResourceNotFoundException("Contact", "ContactID", contactId.toString()));
        contact.setStatus(status);
        contactRepository.save(contact);
    }

    private Contact mapToContact(ContactRequestDto contactRequestDto){
        Contact contact = new Contact();
        BeanUtils.copyProperties(contactRequestDto,contact);
        return contact;
    }

    private ContactResponseDto mapToContactResponseDto (Contact contact){
        ContactResponseDto contactResponseDto =
                new ContactResponseDto(
                        contact.getId(),
                        contact.getName(),
                        contact.getEmail(),
                        contact.getMobileNumber(),
                        contact.getMessage(),
                        contact.getStatus());
        return contactResponseDto;
    }
}
