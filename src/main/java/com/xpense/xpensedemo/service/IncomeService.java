package com.xpense.xpensedemo.service;

import com.xpense.xpensedemo.dto.IncomeDTO;
import com.xpense.xpensedemo.model.Income;
import com.xpense.xpensedemo.repository.IncomeRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IncomeService {

    IncomeRepository repository;

    public IncomeService(IncomeRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void createIncome(IncomeDTO income) {
        repository.save(Income.fromDTO(income));
    }

    public List<Income> getAllIncomes() {
        return repository.findAll();
    }

    public Optional<Income> getIncomeById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public void deleteIncome(Long id) {
        repository.deleteById(id);
    }

    @Transactional
    public void updateIncome(Long id, IncomeDTO income) {
        Optional<Income> existingIncome = repository.findById(id);
        if (existingIncome.isPresent()) {   
            repository.save(Income.fromDTO(income));
        } else {
            throw new RuntimeException("Income not found with id: " + id);
        }
    }
}
