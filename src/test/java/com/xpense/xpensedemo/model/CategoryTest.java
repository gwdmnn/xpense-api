package com.xpense.xpensedemo.model;

import com.xpense.xpensedemo.model.category.Category;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    @Test
    void constructor_ShouldCreateCategoryWithCorrectValues() {
        // Given
        String categoryName = "Food";

        // When
        Category category = new Category(null, categoryName);

        // Then
        assertEquals(categoryName, category.getName());
        assertNull(category.getId());
    }

    @Test
    void noArgsConstructor_ShouldCreateEmptyCategory() {
        // When
        Category category = new Category();

        // Then
        assertNull(category.getId());
        assertNull(category.getName());
    }

    @Test
    void settersAndGetters_ShouldWorkCorrectly() {
        // Given
        Category category = new Category();
        Long expectedId = 1L;
        String expectedName = "Transportation";

        // When
        category.setId(expectedId);
        category.setName(expectedName);

        // Then
        assertEquals(expectedId, category.getId());
        assertEquals(expectedName, category.getName());
    }

    @Test
    void setName_ShouldHandleNullValue() {
        // Given
        Category category = new Category();

        // When
        category.setName(null);

        // Then
        assertNull(category.getName());
    }

    @Test
    void setName_ShouldHandleEmptyString() {
        // Given
        Category category = new Category();
        String emptyName = "";

        // When
        category.setName(emptyName);

        // Then
        assertEquals(emptyName, category.getName());
    }

    @Test
    void setName_ShouldHandleWhitespaceString() {
        // Given
        Category category = new Category();
        String whitespaceName = "   ";

        // When
        category.setName(whitespaceName);

        // Then
        assertEquals(whitespaceName, category.getName());
    }
}
