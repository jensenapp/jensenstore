package com.eazybytes.jensenstore.service;

import com.eazybytes.jensenstore.dto.ContactRequestDto;
import com.eazybytes.jensenstore.dto.ContactResponseDto;
import com.eazybytes.jensenstore.entity.Contact;

import java.util.List;

public interface IContactService {

    boolean saveContact(ContactRequestDto contactRequestDto);

    List<ContactResponseDto> getAllOpenMessages();

    void updateMessageStatus(Long id,String status);

}
