package com.portfolio.backend.repository;

import com.portfolio.backend.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Contact entity operations.
 * Extends JpaRepository to provide basic CRUD operations.
 */
@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
}
