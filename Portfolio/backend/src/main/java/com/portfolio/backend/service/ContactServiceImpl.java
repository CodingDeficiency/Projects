package com.portfolio.backend.service;

import com.portfolio.backend.model.Contact;
import com.portfolio.backend.repository.ContactRepository;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Implementation of the ContactService interface.
 * Provides business logic for contact-related operations.
 */
@Service
public class ContactServiceImpl implements ContactService {
    private final ContactRepository contactRepository;

    /**
     * Constructor for ContactServiceImpl.
     * 
     * @param contactRepository the repository for contact data operations
     */
    public ContactServiceImpl(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    /**
     * Saves a new contact message to the database.
     * 
     * @param contact the contact message to save
     * @return the saved contact with generated ID
     */
    @Override
    public Contact saveContact(Contact contact) {
        return contactRepository.save(contact);
    }

    /**
     * Retrieves all contact messages from the database.
     * 
     * @return a list of all contacts
     */
    @Override
    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }
}
