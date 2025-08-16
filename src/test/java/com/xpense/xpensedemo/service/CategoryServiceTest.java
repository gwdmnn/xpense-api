package com.xpense.xpensedemo.service;

import com.xpense.xpensedemo.dto.CategoryDTO;
import com.xpense.xpensedemo.model.category.Category;
import com.xpense.xpensedemo.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    private CategoryDTO categoryDTO;
    private Category category;

    @BeforeEach
    void setUp() {
        categoryDTO = new CategoryDTO("Food");
        category = new Category(1L, "Food");
    }

    @Test
    void createCategory_ShouldSaveCategory_WhenValidDTOProvided() {
        // Given
        when(categoryRepository.existsByName("Food")).thenReturn(false);
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        // When
        Category result = categoryService.createCategory(categoryDTO);

        // Then
        assertEquals("Food", result.getName());
        verify(categoryRepository, times(1)).existsByName("Food");
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    void createCategory_ShouldThrowException_WhenCategoryNameAlreadyExists() {
        // Given
        when(categoryRepository.existsByName("Food")).thenReturn(true);

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            categoryService.createCategory(categoryDTO);
        });

        assertEquals("Category with name 'Food' already exists", exception.getMessage());
        verify(categoryRepository, times(1)).existsByName("Food");
        verify(categoryRepository, never()).save(any(Category.class));
    }

    @Test
    void createCategory_ShouldCallRepositorySaveWithCorrectData() {
        // Given
        when(categoryRepository.existsByName("Food")).thenReturn(false);
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        // When
        categoryService.createCategory(categoryDTO);

        // Then
        verify(categoryRepository).save(argThat(savedCategory ->
                savedCategory.getName().equals("Food")
        ));
    }

    @Test
    void getAllCategories_ShouldReturnAllCategories() {
        // Given
        Category category1 = new Category(1L, "Food");
        Category category2 = new Category(2L, "Transportation");
        List<Category> expectedCategories = Arrays.asList(category1, category2);

        when(categoryRepository.findAll()).thenReturn(expectedCategories);

        // When
        List<Category> actualCategories = categoryService.getAllCategories();

        // Then
        assertEquals(2, actualCategories.size());
        assertEquals(expectedCategories, actualCategories);
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void getAllCategories_ShouldReturnEmptyList_WhenNoCategoriesExist() {
        // Given
        when(categoryRepository.findAll()).thenReturn(Arrays.asList());

        // When
        List<Category> actualCategories = categoryService.getAllCategories();

        // Then
        assertTrue(actualCategories.isEmpty());
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void getCategoryById_ShouldReturnCategory_WhenCategoryExists() {
        // Given
        Long categoryId = 1L;
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        // When
        Optional<Category> result = categoryService.getCategoryById(categoryId);

        // Then
        assertTrue(result.isPresent());
        assertEquals("Food", result.get().getName());
        assertEquals(categoryId, result.get().getId());
        verify(categoryRepository, times(1)).findById(categoryId);
    }

    @Test
    void getCategoryById_ShouldReturnEmpty_WhenCategoryDoesNotExist() {
        // Given
        Long categoryId = 999L;
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        // When
        Optional<Category> result = categoryService.getCategoryById(categoryId);

        // Then
        assertFalse(result.isPresent());
        verify(categoryRepository, times(1)).findById(categoryId);
    }

    @Test
    void getCategoryByName_ShouldReturnCategory_WhenCategoryExists() {
        // Given
        String categoryName = "Food";
        when(categoryRepository.findByName(categoryName)).thenReturn(Optional.of(category));

        // When
        Optional<Category> result = categoryService.getCategoryByName(categoryName);

        // Then
        assertTrue(result.isPresent());
        assertEquals(categoryName, result.get().getName());
        verify(categoryRepository, times(1)).findByName(categoryName);
    }

    @Test
    void getCategoryByName_ShouldReturnEmpty_WhenCategoryDoesNotExist() {
        // Given
        String categoryName = "NonExistent";
        when(categoryRepository.findByName(categoryName)).thenReturn(Optional.empty());

        // When
        Optional<Category> result = categoryService.getCategoryByName(categoryName);

        // Then
        assertFalse(result.isPresent());
        verify(categoryRepository, times(1)).findByName(categoryName);
    }

    @Test
    void updateCategory_ShouldUpdateCategory_WhenCategoryExistsAndNameIsUnique() {
        // Given
        Long categoryId = 1L;
        CategoryDTO updatedCategoryDTO = new CategoryDTO("Updated Food");
        Category existingCategory = new Category(categoryId, "Food");
        Category updatedCategory = new Category(categoryId, "Updated Food");

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(existingCategory));
        when(categoryRepository.findByName("Updated Food")).thenReturn(Optional.empty());
        when(categoryRepository.save(any(Category.class))).thenReturn(updatedCategory);

        // When
        Category result = categoryService.updateCategory(categoryId, updatedCategoryDTO);

        // Then
        assertEquals("Updated Food", result.getName());
        assertEquals(categoryId, result.getId());
        verify(categoryRepository, times(1)).findById(categoryId);
        verify(categoryRepository, times(1)).findByName("Updated Food");
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    void updateCategory_ShouldThrowException_WhenCategoryDoesNotExist() {
        // Given
        Long categoryId = 999L;
        CategoryDTO updatedCategoryDTO = new CategoryDTO("Updated Food");

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> categoryService.updateCategory(categoryId, updatedCategoryDTO));

        assertEquals("Category not found with id: " + categoryId, exception.getMessage());
        verify(categoryRepository, times(1)).findById(categoryId);
        verify(categoryRepository, never()).save(any(Category.class));
    }

    @Test
    void updateCategory_ShouldThrowException_WhenNameAlreadyExistsInAnotherCategory() {
        // Given
        Long categoryId = 1L;
        CategoryDTO updatedCategoryDTO = new CategoryDTO("Transportation");
        Category existingCategory = new Category(categoryId, "Food");
        Category anotherCategory = new Category(2L, "Transportation");

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(existingCategory));
        when(categoryRepository.findByName("Transportation")).thenReturn(Optional.of(anotherCategory));

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            categoryService.updateCategory(categoryId, updatedCategoryDTO);
        });

        assertEquals("Category with name 'Transportation' already exists", exception.getMessage());
        verify(categoryRepository, times(1)).findById(categoryId);
        verify(categoryRepository, times(1)).findByName("Transportation");
        verify(categoryRepository, never()).save(any(Category.class));
    }

    @Test
    void updateCategory_ShouldAllowSameNameForSameCategory() {
        // Given
        Long categoryId = 1L;
        CategoryDTO updatedCategoryDTO = new CategoryDTO("Food");
        Category existingCategory = new Category(categoryId, "Food");

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(existingCategory));
        when(categoryRepository.findByName("Food")).thenReturn(Optional.of(existingCategory));
        when(categoryRepository.save(any(Category.class))).thenReturn(existingCategory);

        // When
        Category result = categoryService.updateCategory(categoryId, updatedCategoryDTO);

        // Then
        assertEquals("Food", result.getName());
        assertEquals(categoryId, result.getId());
        verify(categoryRepository, times(1)).findById(categoryId);
        verify(categoryRepository, times(1)).findByName("Food");
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    void deleteCategory_ShouldDeleteCategory_WhenCategoryExists() {
        // Given
        Long categoryId = 1L;
        when(categoryRepository.existsById(categoryId)).thenReturn(true);

        // When
        categoryService.deleteCategory(categoryId);

        // Then
        verify(categoryRepository, times(1)).existsById(categoryId);
        verify(categoryRepository, times(1)).deleteById(categoryId);
    }

    @Test
    void deleteCategory_ShouldThrowException_WhenCategoryDoesNotExist() {
        // Given
        Long categoryId = 999L;
        when(categoryRepository.existsById(categoryId)).thenReturn(false);

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            categoryService.deleteCategory(categoryId);
        });

        assertEquals("Category not found with id: " + categoryId, exception.getMessage());
        verify(categoryRepository, times(1)).existsById(categoryId);
        verify(categoryRepository, never()).deleteById(categoryId);
    }
}
