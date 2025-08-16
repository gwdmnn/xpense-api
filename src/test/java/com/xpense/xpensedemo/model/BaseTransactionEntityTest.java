package com.xpense.xpensedemo.model;

import com.xpense.xpensedemo.model.transaction.BaseTransactionEntity;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class BaseTransactionEntityTest {

    private Validator validator;

    // Concrete implementation for testing
    private static class TestTransaction extends BaseTransactionEntity {
        public TestTransaction() {
            super();
        }

        public TestTransaction(double amount, String description, LocalDate date) {
            super(amount, description, date);
        }
    }

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void validTransaction_ShouldPassValidation() {
        // Given
        TestTransaction transaction = new TestTransaction(
                100.50,
                "Valid transaction",
                LocalDate.of(2025, 8, 12)
        );

        // When
        Set<ConstraintViolation<TestTransaction>> violations = validator.validate(transaction);

        // Then
        assertTrue(violations.isEmpty());
    }

    @Test
    void transactionWithBlankDescription_ShouldFailValidation() {
        // Given
        TestTransaction transaction = new TestTransaction(
                100.50,
                "",
                LocalDate.of(2025, 8, 12)
        );

        // When
        Set<ConstraintViolation<TestTransaction>> violations = validator.validate(transaction);

        // Then
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().equals("Description cannot be blank")));
    }

    @Test
    void transactionWithNullDescription_ShouldFailValidation() {
        // Given
        TestTransaction transaction = new TestTransaction(
                100.50,
                null,
                LocalDate.of(2025, 8, 12)
        );

        // When
        Set<ConstraintViolation<TestTransaction>> violations = validator.validate(transaction);

        // Then
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().equals("Description cannot be blank")));
    }

    @Test
    void transactionWithNullDate_ShouldFailValidation() {
        // Given
        TestTransaction transaction = new TestTransaction(
                100.50,
                "Valid description",
                null
        );

        // When
        Set<ConstraintViolation<TestTransaction>> violations = validator.validate(transaction);

        // Then
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().equals("Date cannot be null")));
    }

    @Test
    void transactionWithNegativeAmount_ShouldPassValidation() {
        // Given - Negative amounts should be allowed (for refunds)
        TestTransaction transaction = new TestTransaction(
                -50.00,
                "Refund transaction",
                LocalDate.of(2025, 8, 12)
        );

        // When
        Set<ConstraintViolation<TestTransaction>> violations = validator.validate(transaction);

        // Then
        assertTrue(violations.isEmpty());
    }

    @Test
    void transactionWithZeroAmount_ShouldPassValidation() {
        // Given
        TestTransaction transaction = new TestTransaction(
                0.0,
                "Zero amount transaction",
                LocalDate.of(2025, 8, 12)
        );

        // When
        Set<ConstraintViolation<TestTransaction>> violations = validator.validate(transaction);

        // Then
        assertTrue(violations.isEmpty());
    }

    @Test
    void transactionWithMultipleViolations_ShouldReportAllViolations() {
        // Given
        TestTransaction transaction = new TestTransaction(
                100.50,
                null,
                null
        );

        // When
        Set<ConstraintViolation<TestTransaction>> violations = validator.validate(transaction);

        // Then
        assertEquals(2, violations.size());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().equals("Description cannot be blank")));
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().equals("Date cannot be null")));
    }

    @Test
    void noArgsConstructor_ShouldCreateValidInstance() {
        // Given & When
        TestTransaction transaction = new TestTransaction();

        // Then
        assertNotNull(transaction);
    }

    @Test
    void gettersFromLombok_ShouldReturnCorrectValues() {
        // Given
        double expectedAmount = 150.75;
        String expectedDescription = "Test transaction";
        LocalDate expectedDate = LocalDate.of(2025, 8, 12);

        TestTransaction transaction = new TestTransaction(
                expectedAmount,
                expectedDescription,
                expectedDate
        );

        // When & Then
        assertEquals(expectedAmount, transaction.getAmount());
        assertEquals(expectedDescription, transaction.getDescription());
        assertEquals(expectedDate, transaction.getDate());
        assertNotNull(transaction.getCreatedAt());
    }

    @Test
    void createdAt_ShouldBeSetAutomatically() {
        // Given & When
        TestTransaction transaction = new TestTransaction(
                100.0,
                "Test",
                LocalDate.now()
        );

        // Then
        assertNotNull(transaction.getCreatedAt());
    }
}
