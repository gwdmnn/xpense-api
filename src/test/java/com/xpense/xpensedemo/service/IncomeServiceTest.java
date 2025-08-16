package com.xpense.xpensedemo.service;

import com.xpense.xpensedemo.dto.IncomeDTO;
import com.xpense.xpensedemo.model.transaction.Income;
import com.xpense.xpensedemo.repository.IncomeRepository;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IncomeServiceTest {

    @Mock
    private IncomeRepository incomeRepository;

    @InjectMocks
    private IncomeService incomeService;

    private IncomeDTO incomeDTO;
    private Income income;

    @BeforeEach
    void setUp() {
        incomeDTO = new IncomeDTO(
                "Salary payment",
                "Employment",
                2500.00,
                "2025-08-12"
        );

        income = new Income(2500.00, "Salary payment", LocalDate.of(2025, 8, 12));
    }

    @Test
    void createIncome_ShouldSaveIncome_WhenValidDTOProvided() {
        // Given
        when(incomeRepository.save(any(Income.class))).thenReturn(income);

        // When
        incomeService.createIncome(incomeDTO);

        // Then
        verify(incomeRepository, times(1)).save(any(Income.class));
    }

    @Test
    void createIncome_ShouldCallRepositorySaveWithCorrectData() {
        // Given
        when(incomeRepository.save(any(Income.class))).thenReturn(income);

        // When
        incomeService.createIncome(incomeDTO);

        // Then
        verify(incomeRepository).save(argThat(savedIncome ->
                savedIncome.getAmount() == 2500.00 &&
                savedIncome.getDescription().equals("Salary payment") &&
                savedIncome.getDate().equals(LocalDate.of(2025, 8, 12))
        ));
    }

    @Test
    void getAllIncomes_ShouldReturnAllIncomes() {
        // Given
        Income income1 = new Income(2500.00, "Salary", LocalDate.of(2025, 8, 12));
        Income income2 = new Income(500.00, "Freelance", LocalDate.of(2025, 8, 10));
        List<Income> expectedIncomes = Arrays.asList(income1, income2);

        when(incomeRepository.findAll()).thenReturn(expectedIncomes);

        // When
        List<Income> actualIncomes = incomeService.getAllIncomes();

        // Then
        assertEquals(2, actualIncomes.size());
        assertEquals(expectedIncomes, actualIncomes);
        verify(incomeRepository, times(1)).findAll();
    }

    @Test
    void getAllIncomes_ShouldReturnEmptyList_WhenNoIncomesExist() {
        // Given
        when(incomeRepository.findAll()).thenReturn(Arrays.asList());

        // When
        List<Income> actualIncomes = incomeService.getAllIncomes();

        // Then
        assertTrue(actualIncomes.isEmpty());
        verify(incomeRepository, times(1)).findAll();
    }

    @Test
    void getIncomeById_ShouldReturnIncome_WhenIncomeExists() {
        // Given
        Long incomeId = 1L;
        when(incomeRepository.findById(incomeId)).thenReturn(Optional.of(income));

        // When
        Optional<Income> result = incomeService.getIncomeById(incomeId);

        // Then
        assertTrue(result.isPresent());
        assertEquals(income, result.get());
        verify(incomeRepository, times(1)).findById(incomeId);
    }

    @Test
    void getIncomeById_ShouldReturnEmpty_WhenIncomeDoesNotExist() {
        // Given
        Long incomeId = 99L;
        when(incomeRepository.findById(incomeId)).thenReturn(Optional.empty());

        // When
        Optional<Income> result = incomeService.getIncomeById(incomeId);

        // Then
        assertFalse(result.isPresent());
        verify(incomeRepository, times(1)).findById(incomeId);
    }

    @Test
    void deleteIncome_ShouldCallRepositoryDeleteById() {
        // Given
        Long incomeId = 1L;

        // When
        incomeService.deleteIncome(incomeId);

        // Then
        verify(incomeRepository, times(1)).deleteById(incomeId);
    }

    @Test
    void deleteIncome_ShouldCallRepositoryDeleteById_WithCorrectId() {
        // Given
        Long incomeId = 42L;

        // When
        incomeService.deleteIncome(incomeId);

        // Then
        verify(incomeRepository).deleteById(eq(incomeId));
    }

    @Test
    void createIncome_ShouldHandleNullDTO() {
        // Given
        IncomeDTO nullDTO = null;

        // When & Then
        assertThrows(NullPointerException.class, () -> {
            incomeService.createIncome(nullDTO);
        });

        verify(incomeRepository, never()).save(any(Income.class));
    }

    @Test
    void getIncomeById_ShouldHandleNullId() {
        // Given
        Long nullId = null;

        // When
        incomeService.getIncomeById(nullId);

        // Then
        verify(incomeRepository, times(1)).findById(nullId);
    }
}
