package com.eazybytes.jensenstore.service;

import com.eazybytes.jensenstore.dto.ContactRequestDto;

public interface IContactService {
    boolean saveContact(ContactRequestDto contactRequestDto);
}
