package com.xpense.xpensedemo.repository;

import com.xpense.xpensedemo.model.Output;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutputRepository extends JpaRepository<Output, Long> {
}
