package com.xpense.xpensedemo.service;

import com.xpense.xpensedemo.dto.OutputDTO;
import com.xpense.xpensedemo.model.category.Category;
import com.xpense.xpensedemo.model.transaction.Output;
import com.xpense.xpensedemo.repository.OutputRepository;
import com.xpense.xpensedemo.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OutputServiceTest {

    @Mock
    private OutputRepository outputRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private OutputService outputService;

    private OutputDTO outputDTO;
    private Output output;
    private Category category;

    @BeforeEach
    void setUp() {
        outputDTO = new OutputDTO(
                "Grocery shopping",
                "Food",
                125.50,
                "2025-08-12"
        );

        category = new Category(1L, "Food");
        output = new Output(125.50, "Grocery shopping", LocalDate.of(2025, 8, 12), category);
    }

    @Test
    void createOutput_ShouldSaveOutput_WhenValidDTOProvided() {
        // Given
        when(categoryRepository.findByName("Food")).thenReturn(Optional.of(category));
        when(outputRepository.save(any(Output.class))).thenReturn(output);

        // When
        outputService.createOutput(outputDTO);

        // Then
        verify(outputRepository, times(1)).save(any(Output.class));
    }

    @Test
    void createOutput_ShouldCallRepositorySaveWithCorrectData() {
        // Given
        when(categoryRepository.findByName("Food")).thenReturn(Optional.of(category));
        when(outputRepository.save(any(Output.class))).thenReturn(output);

        // When
        outputService.createOutput(outputDTO);

        // Then
        verify(outputRepository).save(argThat(savedOutput ->
                savedOutput.getAmount() == 125.50 &&
                savedOutput.getDescription().equals("Grocery shopping") &&
                savedOutput.getDate().equals(LocalDate.of(2025, 8, 12))
        ));
    }

    @Test
    void getAllOutputs_ShouldReturnAllOutputs() {
        // Given
        Output output1 = new Output(125.50, "Groceries", LocalDate.of(2025, 8, 12));
        Output output2 = new Output(75.00, "Gas", LocalDate.of(2025, 8, 10));
        List<Output> expectedOutputs = Arrays.asList(output1, output2);

        when(outputRepository.findAll()).thenReturn(expectedOutputs);

        // When
        List<Output> actualOutputs = outputService.getAllOutputs();

        // Then
        assertEquals(2, actualOutputs.size());
        assertEquals(expectedOutputs, actualOutputs);
        verify(outputRepository, times(1)).findAll();
    }

    @Test
    void getAllOutputs_ShouldReturnEmptyList_WhenNoOutputsExist() {
        // Given
        when(outputRepository.findAll()).thenReturn(Collections.emptyList());

        // When
        List<Output> actualOutputs = outputService.getAllOutputs();

        // Then
        assertTrue(actualOutputs.isEmpty());
        verify(outputRepository, times(1)).findAll();
    }

    @Test
    void getOutputById_ShouldReturnOutput_WhenOutputExists() {
        // Given
        Long outputId = 1L;
        when(outputRepository.findById(outputId)).thenReturn(Optional.of(output));

        // When
        Optional<Output> result = outputService.getOutputById(outputId);

        // Then
        assertTrue(result.isPresent());
        assertEquals("Grocery shopping", result.get().getDescription());
        assertEquals(125.50, result.get().getAmount());
        verify(outputRepository, times(1)).findById(outputId);
    }

    @Test
    void getOutputById_ShouldReturnEmpty_WhenOutputDoesNotExist() {
        // Given
        Long outputId = 999L;
        when(outputRepository.findById(outputId)).thenReturn(Optional.empty());

        // When
        Optional<Output> result = outputService.getOutputById(outputId);

        // Then
        assertFalse(result.isPresent());
        verify(outputRepository, times(1)).findById(outputId);
    }

    @Test
    void deleteOutput_ShouldDeleteOutput_WhenOutputExists() {
        // Given
        Long outputId = 1L;

        // When
        outputService.deleteOutput(outputId);

        // Then
        verify(outputRepository, times(1)).deleteById(outputId);
    }

    @Test
    void updateOutput_ShouldUpdateOutput_WhenOutputExists() {
        // Given
        Long outputId = 1L;
        OutputDTO updatedOutputDTO = new OutputDTO(
                "Updated grocery shopping",
                "Food",
                150.00,
                "2025-08-12"
        );
        Output existingOutput = new Output(125.50, "Grocery shopping", LocalDate.of(2025, 8, 12));

        when(outputRepository.findById(outputId)).thenReturn(Optional.of(existingOutput));
        when(categoryRepository.findByName("Food")).thenReturn(Optional.of(category));

        // When
        outputService.updateOutput(outputId, updatedOutputDTO);

        // Then
        verify(outputRepository, times(1)).findById(outputId);
        verify(categoryRepository, times(1)).findByName("Food");
        verify(outputRepository, times(1)).save(any(Output.class));
    }

    @Test
    void updateOutput_ShouldThrowException_WhenOutputDoesNotExist() {
        // Given
        Long outputId = 999L;
        OutputDTO updatedOutputDTO = new OutputDTO(
                "Updated grocery shopping",
                "Food",
                150.00,
                "2025-08-12"
        );

        when(outputRepository.findById(outputId)).thenReturn(Optional.empty());

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
            outputService.updateOutput(outputId, updatedOutputDTO)
        );

        assertEquals("Output not found with id: " + outputId, exception.getMessage());
        verify(outputRepository, times(1)).findById(outputId);
        verify(outputRepository, never()).save(any(Output.class));
    }

    // Tests for Category-related functionality
    @Test
    void createOutput_ShouldAssignCategory_WhenCategoryExists() {
        // Given
        when(categoryRepository.findByName("Food")).thenReturn(Optional.of(category));
        when(outputRepository.save(any(Output.class))).thenReturn(output);

        // When
        outputService.createOutput(outputDTO);

        // Then
        verify(categoryRepository, times(1)).findByName("Food");
        verify(outputRepository).save(argThat(savedOutput ->
                savedOutput.getCategory() != null &&
                savedOutput.getCategory().getName().equals("Food")
        ));
    }

    @Test
    void createOutput_ShouldCreateWithoutCategory_WhenCategoryDoesNotExist() {
        // Given
        when(categoryRepository.findByName("Food")).thenReturn(Optional.empty());
        Output outputWithoutCategory = new Output(125.50, "Grocery shopping", LocalDate.of(2025, 8, 12));
        when(outputRepository.save(any(Output.class))).thenReturn(outputWithoutCategory);

        // When
        outputService.createOutput(outputDTO);

        // Then
        verify(categoryRepository, times(1)).findByName("Food");
        verify(outputRepository).save(argThat(savedOutput ->
                savedOutput.getCategory() == null
        ));
    }

    @Test
    void getAllOutputs_ShouldReturnOutputsWithCategories() {
        // Given
        Category foodCategory = new Category(1L, "Food");
        Category transportCategory = new Category(2L, "Transportation");

        Output output1 = new Output(125.50, "Groceries", LocalDate.of(2025, 8, 12), foodCategory);
        Output output2 = new Output(75.00, "Gas", LocalDate.of(2025, 8, 10), transportCategory);
        List<Output> expectedOutputs = Arrays.asList(output1, output2);

        when(outputRepository.findAll()).thenReturn(expectedOutputs);

        // When
        List<Output> actualOutputs = outputService.getAllOutputs();

        // Then
        assertEquals(2, actualOutputs.size());
        assertEquals("Food", actualOutputs.get(0).getCategory().getName());
        assertEquals("Transportation", actualOutputs.get(1).getCategory().getName());
        verify(outputRepository, times(1)).findAll();
    }
}
