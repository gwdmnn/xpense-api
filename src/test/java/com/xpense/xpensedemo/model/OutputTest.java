package com.xpense.xpensedemo.model;

import com.xpense.xpensedemo.dto.OutputDTO;
import com.xpense.xpensedemo.model.category.Category;
import com.xpense.xpensedemo.model.transaction.Output;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class OutputTest {

    @Test
    void fromDTO_ShouldCreateOutputWithCorrectValues() {
        // Given
        OutputDTO outputDTO = new OutputDTO(
                "Grocery shopping",
                "Food",
                125.50,
                "2025-08-12"
        );

        // When
        Output output = Output.fromDTO(outputDTO);

        // Then
        assertEquals(125.50, output.getAmount());
        assertEquals("Grocery shopping", output.getDescription());
        assertEquals(LocalDate.of(2025, 8, 12), output.getDate());
        assertNull(output.getCategory()); // Category is not set in fromDTO method
    }

    @Test
    void fromDTO_ShouldHandleNegativeAmount_ForRefunds() {
        // Given - Negative amounts for refunds
        OutputDTO outputDTO = new OutputDTO(
                "Refund for returned item",
                "Refund",
                -75.00,
                "2025-08-12"
        );

        // When
        Output output = Output.fromDTO(outputDTO);

        // Then
        assertEquals(-75.00, output.getAmount());
        assertEquals("Refund for returned item", output.getDescription());
        assertEquals(LocalDate.of(2025, 8, 12), output.getDate());
    }

    @Test
    void fromDTO_ShouldHandleZeroAmount() {
        // Given
        OutputDTO outputDTO = new OutputDTO(
                "Zero amount transaction",
                "Test",
                0.0,
                "2025-08-12"
        );

        // When
        Output output = Output.fromDTO(outputDTO);

        // Then
        assertEquals(0.0, output.getAmount());
        assertEquals("Zero amount transaction", output.getDescription());
        assertEquals(LocalDate.of(2025, 8, 12), output.getDate());
    }

    @Test
    void fromDTO_ShouldHandleLargeAmount() {
        // Given
        OutputDTO outputDTO = new OutputDTO(
                "Large purchase",
                "Investment",
                15000.99,
                "2025-08-12"
        );

        // When
        Output output = Output.fromDTO(outputDTO);

        // Then
        assertEquals(15000.99, output.getAmount());
        assertEquals("Large purchase", output.getDescription());
        assertEquals(LocalDate.of(2025, 8, 12), output.getDate());
    }

    @Test
    void fromDTO_ShouldThrowException_WhenInvalidDateFormat() {
        // Given
        OutputDTO outputDTO = new OutputDTO(
                "Invalid date output",
                "Test",
                100.0,
                "invalid-date"
        );

        // When & Then
        assertThrows(Exception.class, () -> {
            Output.fromDTO(outputDTO);
        });
    }

    @Test
    void fromDTO_ShouldHandleDifferentDateFormats() {
        // Given
        OutputDTO outputDTO = new OutputDTO(
                "Different date format",
                "Test",
                100.0,
                "2025-12-31"
        );

        // When
        Output output = Output.fromDTO(outputDTO);

        // Then
        assertEquals(100.0, output.getAmount());
        assertEquals("Different date format", output.getDescription());
        assertEquals(LocalDate.of(2025, 12, 31), output.getDate());
    }

    @Test
    void constructor_ShouldCreateOutputWithCorrectValues() {
        // Given
        double amount = 89.99;
        String description = "Coffee shop";
        LocalDate date = LocalDate.of(2025, 8, 12);

        // When
        Output output = new Output(amount, description, date);

        // Then
        assertEquals(amount, output.getAmount());
        assertEquals(description, output.getDescription());
        assertEquals(date, output.getDate());
        assertNotNull(output.getCreatedAt());
    }

    @Test
    void noArgsConstructor_ShouldCreateValidInstance() {
        // Given & When
        Output output = new Output();

        // Then
        assertNotNull(output);
    }

    @Test
    void fromDTO_ShouldHandleSpecialCharacters() {
        // Given
        OutputDTO outputDTO = new OutputDTO(
                "Special café & restaurant ñ",
                "Food & Dining",
                45.67,
                "2025-08-12"
        );

        // When
        Output output = Output.fromDTO(outputDTO);

        // Then
        assertEquals(45.67, output.getAmount());
        assertEquals("Special café & restaurant ñ", output.getDescription());
        assertEquals(LocalDate.of(2025, 8, 12), output.getDate());
    }

    @Test
    void constructor_ShouldCreateOutputWithCategory() {
        // Given
        Category category = new Category(1L, "Food");
        double amount = 125.50;
        String description = "Grocery shopping";
        LocalDate date = LocalDate.of(2025, 8, 12);

        // When
        Output output = new Output(amount, description, date, category);

        // Then
        assertEquals(amount, output.getAmount());
        assertEquals(description, output.getDescription());
        assertEquals(date, output.getDate());
        assertEquals(category, output.getCategory());
        assertEquals("Food", output.getCategory().getName());
    }

    @Test
    void constructor_ShouldCreateOutputWithoutCategory() {
        // Given
        double amount = 125.50;
        String description = "Grocery shopping";
        LocalDate date = LocalDate.of(2025, 8, 12);

        // When
        Output output = new Output(amount, description, date);

        // Then
        assertEquals(amount, output.getAmount());
        assertEquals(description, output.getDescription());
        assertEquals(date, output.getDate());
        assertNull(output.getCategory());
    }

    @Test
    void setCategory_ShouldAssignCategory() {
        // Given
        Output output = new Output(100.0, "Test expense", LocalDate.now());
        Category category = new Category(1L, "Transportation");

        // When
        output.setCategory(category);

        // Then
        assertEquals(category, output.getCategory());
        assertEquals("Transportation", output.getCategory().getName());
    }

    @Test
    void setCategory_ShouldHandleNullCategory() {
        // Given
        Category initialCategory = new Category(1L, "Food");
        Output output = new Output(100.0, "Test expense", LocalDate.now(), initialCategory);

        // When
        output.setCategory(null);

        // Then
        assertNull(output.getCategory());
    }

    @Test
    void getCategory_ShouldReturnAssignedCategory() {
        // Given
        Category category = new Category(2L, "Entertainment");
        Output output = new Output(50.0, "Movie tickets", LocalDate.now(), category);

        // When
        Category retrievedCategory = output.getCategory();

        // Then
        assertNotNull(retrievedCategory);
        assertEquals(category.getId(), retrievedCategory.getId());
        assertEquals(category.getName(), retrievedCategory.getName());
    }
}
