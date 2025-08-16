package com.xpense.xpensedemo.service;

import com.xpense.xpensedemo.dto.OutputDTO;
import com.xpense.xpensedemo.model.category.Category;
import com.xpense.xpensedemo.model.transaction.Output;
import com.xpense.xpensedemo.repository.CategoryRepository;
import com.xpense.xpensedemo.repository.OutputRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OutputService {

    private final OutputRepository repository;
    private final CategoryRepository categoryRepository;

    public OutputService(OutputRepository repository, CategoryRepository categoryRepository) {
        this.repository = repository;
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public void createOutput(OutputDTO outputDTO) {
        Output output = Output.fromDTO(outputDTO);

        Optional<Category> category = categoryRepository.findByName(outputDTO.category());
        category.ifPresent(output::setCategory);

        repository.save(output);
    }

    public List<Output> getAllOutputs() {
        return repository.findAll();
    }

    public Optional<Output> getOutputById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public void updateOutput(Long id, OutputDTO outputDTO) {
        Optional<Output> existingOutput = repository.findById(id);
        if (existingOutput.isPresent()) {
            Output output = Output.fromDTO(outputDTO);
            output.setId(id);

            // Buscar categoria pelo nome se fornecido
            Optional<Category> category = categoryRepository.findByName(outputDTO.category());
            category.ifPresent(output::setCategory);

            repository.save(output);
        } else {
            throw new RuntimeException("Output not found with id: " + id);
        }
    }

    @Transactional
    public void deleteOutput(Long id) {
        repository.deleteById(id);
    }
}
