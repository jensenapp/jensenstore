package com.eazybytes.jensenstore.repository;

import com.eazybytes.jensenstore.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact,Long> {
}
