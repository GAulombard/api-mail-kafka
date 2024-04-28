package com.hodor.apimail.entity;

import com.hodor.apimail.enumaration.EmailPriority;
import com.hodor.apimail.enumaration.EmailStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
public class Email {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="\"from\"")
    private String from;
    @Column(name="\"to\"")
    private String to;
    private String subject;
    @Column(columnDefinition = "TEXT")
    private String body;
    @Enumerated(EnumType.STRING)
    private EmailStatus status;
    @Enumerated(EnumType.STRING)
    private EmailPriority priority;
    private String failedReason;

    @CreationTimestamp
    @Column(nullable = false, insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime creationDate = LocalDateTime.now();

}
