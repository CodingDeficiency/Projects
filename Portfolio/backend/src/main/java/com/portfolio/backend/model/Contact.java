package com.portfolio.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;

/**
 * Represents a contact message from a visitor to the portfolio owner.
 * This entity maps to the 'contacts' table in the database.
 */
@Data
@Entity
@Table(name = "contacts")
public class Contact {

    /**
     * Unique identifier for the contact message.
     * Auto-incremented primary key.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Name of the person sending the message.
     * Cannot be null. making it a required field.
     */
    @Column(nullable = false)
    private String name;

    /**
     * Email address of the sender.
     * Cannot be null. making it a required field.
     */
    @Column(nullable = false)
    private String email;

    /**
     * The actual message content.
     * Cannot be null. making it a required field.
     */
    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    /**
     * Timestamp when the contact message was created.
     * Automatically set when the contact is created.
     */
    @CreationTimestamp
    private LocalDateTime createdAt;
}
