package com.xpense.xpensedemo.model;

import com.xpense.xpensedemo.dto.IncomeDTO;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "income")
@NoArgsConstructor
public class Income extends BaseTransactionEntity {

    public Income(double amount, String description, LocalDate date) {
        super(amount, description, date);
    }

    public static Income fromDTO(IncomeDTO incomeDTO) {
        return new Income(
                incomeDTO.amount(),
                incomeDTO.description(),
                LocalDate.parse(incomeDTO.date())
        );
    }
}
