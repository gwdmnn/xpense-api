package com.xpense.xpensedemo.service;

import com.xpense.xpensedemo.dto.OutputDTO;
import com.xpense.xpensedemo.model.Output;
import com.xpense.xpensedemo.repository.OutputRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OutputServiceTest {

    @Mock
    private OutputRepository outputRepository;

    @InjectMocks
    private OutputService outputService;

    private OutputDTO outputDTO;
    private Output output;

    @BeforeEach
    void setUp() {
        outputDTO = new OutputDTO(
                "Grocery shopping",
                "Food",
                125.50,
                "2025-08-12"
        );

        output = new Output(125.50, "Grocery shopping", LocalDate.of(2025, 8, 12));
    }

    @Test
    void createOutput_ShouldSaveOutput_WhenValidDTOProvided() {
        // Given
        when(outputRepository.save(any(Output.class))).thenReturn(output);

        // When
        outputService.createOutput(outputDTO);

        // Then
        verify(outputRepository, times(1)).save(any(Output.class));
    }

    @Test
    void createOutput_ShouldCallRepositorySaveWithCorrectData() {
        // Given
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
    void createOutput_ShouldHandleNegativeAmount() {
        // Given - Testing refund scenario
        OutputDTO refundDTO = new OutputDTO(
                "Refund for returned item",
                "Refund",
                -50.00,
                "2025-08-12"
        );
        Output refundOutput = new Output(-50.00, "Refund for returned item", LocalDate.of(2025, 8, 12));
        
        when(outputRepository.save(any(Output.class))).thenReturn(refundOutput);

        // When
        outputService.createOutput(refundDTO);

        // Then
        verify(outputRepository).save(argThat(savedOutput ->
                savedOutput.getAmount() == -50.00 &&
                savedOutput.getDescription().equals("Refund for returned item")
        ));
    }

    @Test
    void getAllOutputs_ShouldReturnAllOutputs() {
        // Given
        Output output1 = new Output(125.50, "Groceries", LocalDate.of(2025, 8, 12));
        Output output2 = new Output(45.00, "Gas", LocalDate.of(2025, 8, 11));
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
        when(outputRepository.findAll()).thenReturn(Arrays.asList());

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
        assertEquals(output, result.get());
        verify(outputRepository, times(1)).findById(outputId);
    }

    @Test
    void getOutputById_ShouldReturnEmpty_WhenOutputDoesNotExist() {
        // Given
        Long outputId = 99L;
        when(outputRepository.findById(outputId)).thenReturn(Optional.empty());

        // When
        Optional<Output> result = outputService.getOutputById(outputId);

        // Then
        assertFalse(result.isPresent());
        verify(outputRepository, times(1)).findById(outputId);
    }

    @Test
    void deleteOutput_ShouldCallRepositoryDeleteById() {
        // Given
        Long outputId = 1L;

        // When
        outputService.deleteOutput(outputId);

        // Then
        verify(outputRepository, times(1)).deleteById(outputId);
    }

    @Test
    void deleteOutput_ShouldCallRepositoryDeleteById_WithCorrectId() {
        // Given
        Long outputId = 42L;

        // When
        outputService.deleteOutput(outputId);

        // Then
        verify(outputRepository).deleteById(eq(outputId));
    }

    @Test
    void createOutput_ShouldHandleNullDTO() {
        // Given
        OutputDTO nullDTO = null;

        // When & Then
        assertThrows(NullPointerException.class, () -> {
            outputService.createOutput(nullDTO);
        });

        verify(outputRepository, never()).save(any(Output.class));
    }

    @Test
    void getOutputById_ShouldHandleNullId() {
        // Given
        Long nullId = null;

        // When
        outputService.getOutputById(nullId);

        // Then
        verify(outputRepository, times(1)).findById(nullId);
    }

    @Test
    void createOutput_ShouldHandleZeroAmount() {
        // Given
        OutputDTO zeroAmountDTO = new OutputDTO(
                "Zero amount transaction",
                "Test",
                0.0,
                "2025-08-12"
        );
        Output zeroOutput = new Output(0.0, "Zero amount transaction", LocalDate.of(2025, 8, 12));
        
        when(outputRepository.save(any(Output.class))).thenReturn(zeroOutput);

        // When
        outputService.createOutput(zeroAmountDTO);

        // Then
        verify(outputRepository).save(argThat(savedOutput ->
                savedOutput.getAmount() == 0.0 &&
                savedOutput.getDescription().equals("Zero amount transaction")
        ));
    }

    @Test
    void createOutput_ShouldHandleLargeAmount() {
        // Given
        OutputDTO largeAmountDTO = new OutputDTO(
                "Large purchase",
                "Investment",
                10000.99,
                "2025-08-12"
        );
        Output largeOutput = new Output(10000.99, "Large purchase", LocalDate.of(2025, 8, 12));
        
        when(outputRepository.save(any(Output.class))).thenReturn(largeOutput);

        // When
        outputService.createOutput(largeAmountDTO);

        // Then
        verify(outputRepository).save(argThat(savedOutput ->
                savedOutput.getAmount() == 10000.99 &&
                savedOutput.getDescription().equals("Large purchase")
        ));
    }
}
