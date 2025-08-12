package com.xpense.xpensedemo.model;

import com.xpense.xpensedemo.dto.OutputDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Table(name = "output")
@Entity
@NoArgsConstructor
public class Output extends BaseTransactionEntity {

    public Output(double amount, String description, LocalDate date) {
        super(amount, description, date);
    }

    public static Output fromDTO(OutputDTO outputDTO) {
        return new Output(
                outputDTO.amount(),
                outputDTO.description(),
                LocalDate.parse(outputDTO.date())
        );
    }
}
