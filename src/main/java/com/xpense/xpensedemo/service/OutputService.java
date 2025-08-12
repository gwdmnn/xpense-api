package com.xpense.xpensedemo.service;

import com.xpense.xpensedemo.dto.OutputDTO;
import com.xpense.xpensedemo.model.Output;
import com.xpense.xpensedemo.repository.OutputRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OutputService {

    OutputRepository repository;

    public OutputService(OutputRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void createOutput(OutputDTO output) {
        repository.save(Output.fromDTO(output));
    }

    public List<Output> getAllOutputs() {
        return repository.findAll();
    }

    public Optional<Output> getOutputById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public void deleteOutput(Long id) {
        repository.deleteById(id);
    }
}
