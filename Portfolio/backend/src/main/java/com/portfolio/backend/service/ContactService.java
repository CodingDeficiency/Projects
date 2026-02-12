package com.portfolio.backend.service;

import com.portfolio.backend.model.Contact;
import java.util.List;

/**
 * Service interface for contact-related operations.
 * Defines the contract for contact business logic.
 */
public interface ContactService {

    /**
     * Saves a new contact message to the database.
     * 
     * @param contact the contact message to save
     * @return the saved contact with generated ID
     */
    Contact saveContact(Contact contact);

    /**
     * Retrieves all contact messages from the database.
     * 
     * @return a list of all contacts
     */
    List<Contact> getAllContacts();
}
