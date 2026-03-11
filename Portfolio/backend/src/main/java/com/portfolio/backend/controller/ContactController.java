package com.portfolio.backend.controller;

import com.portfolio.backend.model.Contact;
import com.portfolio.backend.service.ContactService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * REST controller for handling contact-related HTTP requests.
 * Provides endpoints for visitors to send messages to portfolio owner.
 */
@RestController
@RequestMapping("/api/contacts")
@CrossOrigin(origins = "http://localhost:4200") // Allow Angular frontend
public class ContactController {
    private final ContactService contactService;

    /**
     * Constructor for ContactController.
     * 
     * @param contactService the service for contact business logic
     */
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    /**
     * Handles POST requests to submit a new contact message.
     * 
     * @param contact the contact message from the request body
     * @return the saved contact with HTTP 200 status
     */
    @PostMapping
    public ResponseEntity<Contact> submitContact(@RequestBody Contact contact) {
        Contact savedContact = contactService.saveContact(contact);
        return ResponseEntity.ok(savedContact);
    }

    /**
     * Handles GET requests to retrieve all contact messages.
     * 
     * @return a list of all contacts with HTTP 200 status
     */
    @GetMapping
    public ResponseEntity<List<Contact>> getAllContacts() {
        List<Contact> contacts = contactService.getAllContacts();
        return ResponseEntity.ok(contacts);
    }
}
