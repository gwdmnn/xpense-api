package com.xpense.xpensedemo.model.transaction;

import com.xpense.xpensedemo.dto.OutputDTO;
import com.xpense.xpensedemo.model.category.Category;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Table(name = "OUTPUT")
@Entity
@NoArgsConstructor
public class Output extends BaseTransactionEntity {

    @OneToOne
    private Category category;

    public Output(double amount, String description, LocalDate date) {
        super(amount, description, date);
    }

    public Output(double amount, String description, LocalDate date, Category category) {
        super(amount, description, date);
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public static Output fromDTO(OutputDTO outputDTO) {
        return new Output(
                outputDTO.amount(),
                outputDTO.description(),
                LocalDate.parse(outputDTO.date())
        );
    }
}
