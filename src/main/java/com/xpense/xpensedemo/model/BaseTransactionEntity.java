package com.xpense.xpensedemo.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@MappedSuperclass
@NoArgsConstructor
@Getter
public abstract class BaseTransactionEntity {

    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id
    private Long id;
    
    @NotNull(message = "Amount cannot be null")
    private double amount;
    
    @NotBlank(message = "Description cannot be blank")
    private String description;
    
    @NotNull(message = "Date cannot be null")
    private LocalDate date;
    
    private Timestamp createdAt = Timestamp.valueOf(LocalDateTime.now());
    
    // Constructor for creating new entities (without id)
    public BaseTransactionEntity(double amount, String description, LocalDate date) {
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.createdAt = Timestamp.valueOf(LocalDateTime.now());
    }
}
