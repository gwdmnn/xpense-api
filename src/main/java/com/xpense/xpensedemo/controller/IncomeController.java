package com.xpense.xpensedemo.controller;

import com.xpense.xpensedemo.dto.IncomeDTO;
import com.xpense.xpensedemo.service.IncomeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/incomes")
public class IncomeController {

    @Autowired
    IncomeService incomeService;

    @PostMapping("/create")
    public ResponseEntity<String> createIncome(@Valid @RequestBody IncomeDTO income) {
        incomeService.createIncome(income);
        return ResponseEntity.ok("Income created successfully");
    }

    @GetMapping
    public ResponseEntity<?> getAllIncomes() {
        return ResponseEntity.ok(incomeService.getAllIncomes());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateIncome(@PathVariable Long id, @Valid @RequestBody IncomeDTO income) {
        incomeService.updateIncome(id, income);
        return ResponseEntity.ok("Income updated successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getIncomeById(@PathVariable Long id) {
        return ResponseEntity.ok(incomeService.getIncomeById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteIncome(@PathVariable Long id) {
        incomeService.deleteIncome(id);
        return ResponseEntity.ok("Income deleted successfully");
    }
}
