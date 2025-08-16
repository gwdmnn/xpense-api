package com.xpense.xpensedemo.repository;

import com.xpense.xpensedemo.model.transaction.Income;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncomeRepository extends JpaRepository<Income, Long> {
}
