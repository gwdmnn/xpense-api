package com.xpense.xpensedemo.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@DisplayName("Service Layer Integration Tests")
class ServiceIntegrationTest {

    @Nested
    @DisplayName("Income Service Integration")
    class IncomeServiceIntegration {

        @Test
        @DisplayName("Spring context should load successfully")
        void contextLoads() {
            // This test ensures that the Spring context loads properly
            // with all beans configured correctly
        }
    }

    @Nested
    @DisplayName("Output Service Integration")
    class OutputServiceIntegration {

        @Test
        @DisplayName("Spring context should load successfully")
        void contextLoads() {
            // This test ensures that the Spring context loads properly
            // with all beans configured correctly
        }
    }
}
