package com.hodor.apimail.entity;

import com.hodor.apimail.enumaration.EmailPriority;
import com.hodor.apimail.enumaration.EmailStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class BulkEmail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="\"from\"")
    private String from;

    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "\"to\"", joinColumns = @JoinColumn(name = "bulk_email_id"))
    @Column(name="\"to\"")
    private List<String> to;
    private String subject;
    @Column(columnDefinition = "TEXT")
    private String body;

    @Enumerated(EnumType.STRING)
    private EmailPriority priority;

    @CreationTimestamp
    @Column(nullable = false, insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime creationDate = LocalDateTime.now();

}
