package com.xpense.xpensedemo.model;

import com.xpense.xpensedemo.dto.IncomeDTO;
import com.xpense.xpensedemo.model.transaction.Income;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class IncomeTest {

    @Test
    void fromDTO_ShouldCreateIncomeWithCorrectValues() {
        // Given
        IncomeDTO incomeDTO = new IncomeDTO(
                "Salary payment",
                "Employment",
                2500.00,
                "2025-08-12"
        );

        // When
        Income income = Income.fromDTO(incomeDTO);

        // Then
        assertEquals(2500.00, income.getAmount());
        assertEquals("Salary payment", income.getDescription());
        assertEquals(LocalDate.of(2025, 8, 12), income.getDate());
    }

    @Test
    void fromDTO_ShouldHandleNegativeAmount() {
        // Given - Though income should typically be positive, testing edge case
        IncomeDTO incomeDTO = new IncomeDTO(
                "Adjustment",
                "Correction",
                -100.00,
                "2025-08-12"
        );

        // When
        Income income = Income.fromDTO(incomeDTO);

        // Then
        assertEquals(-100.00, income.getAmount());
        assertEquals("Adjustment", income.getDescription());
        assertEquals(LocalDate.of(2025, 8, 12), income.getDate());
    }

    @Test
    void fromDTO_ShouldHandleZeroAmount() {
        // Given
        IncomeDTO incomeDTO = new IncomeDTO(
                "Zero income",
                "Test",
                0.0,
                "2025-08-12"
        );

        // When
        Income income = Income.fromDTO(incomeDTO);

        // Then
        assertEquals(0.0, income.getAmount());
        assertEquals("Zero income", income.getDescription());
        assertEquals(LocalDate.of(2025, 8, 12), income.getDate());
    }

    @Test
    void fromDTO_ShouldHandleLargeAmount() {
        // Given
        IncomeDTO incomeDTO = new IncomeDTO(
                "Large bonus",
                "Bonus",
                50000.99,
                "2025-08-12"
        );

        // When
        Income income = Income.fromDTO(incomeDTO);

        // Then
        assertEquals(50000.99, income.getAmount());
        assertEquals("Large bonus", income.getDescription());
        assertEquals(LocalDate.of(2025, 8, 12), income.getDate());
    }

    @Test
    void fromDTO_ShouldThrowException_WhenInvalidDateFormat() {
        // Given
        IncomeDTO incomeDTO = new IncomeDTO(
                "Invalid date income",
                "Test",
                100.0,
                "invalid-date"
        );

        // When & Then
        assertThrows(Exception.class, () -> {
            Income.fromDTO(incomeDTO);
        });
    }

    @Test
    void constructor_ShouldCreateIncomeWithCorrectValues() {
        // Given
        double amount = 1500.50;
        String description = "Freelance work";
        LocalDate date = LocalDate.of(2025, 8, 12);

        // When
        Income income = new Income(amount, description, date);

        // Then
        assertEquals(amount, income.getAmount());
        assertEquals(description, income.getDescription());
        assertEquals(date, income.getDate());
        assertNotNull(income.getCreatedAt());
    }

    @Test
    void noArgsConstructor_ShouldCreateValidInstance() {
        // Given & When
        Income income = new Income();

        // Then
        assertNotNull(income);
    }
}
