package com.xpense.xpensedemo.controller;

import com.xpense.xpensedemo.dto.OutputDTO;
import com.xpense.xpensedemo.service.OutputService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/outputs")
public class OutputController {

    @Autowired
    OutputService outputService;

    @PostMapping("/create")
    public ResponseEntity<String> createOutput(@Valid @RequestBody OutputDTO output) {
        outputService.createOutput(output);
        return ResponseEntity.ok("Output created successfully");
    }

    @GetMapping
    public ResponseEntity<?> getAllOutputs() {
        return ResponseEntity.ok(outputService.getAllOutputs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOutputById(@PathVariable Long id) {
        return ResponseEntity.ok(outputService.getOutputById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOutput(@PathVariable Long id) {
        outputService.deleteOutput(id);
        return ResponseEntity.ok("Output deleted successfully");
    }
}
