package com.xpense.xpensedemo.model.transaction;

import com.xpense.xpensedemo.dto.IncomeDTO;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "INCOME")
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
