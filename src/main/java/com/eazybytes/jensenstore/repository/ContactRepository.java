package com.eazybytes.jensenstore.repository;

import com.eazybytes.jensenstore.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact,Long> {
    List<Contact> findByStatus(String status);
}
