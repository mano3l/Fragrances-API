package com.personal.fragrances.service;

import com.personal.fragrances.domain.Fragrance;
import com.personal.fragrances.repository.FragranceRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
public class FragranceServiceTests {

    @Autowired
    private FragranceRepository fragranceRepository;

    @Autowired
    private FragranceService fragranceService;

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:16-alpine"
    );

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Test
    void shouldRetrieveFragrance() {
        Fragrance savedFragrance = fragranceRepository.save(new Fragrance("Eros"));

        Fragrance retrievedFragrance = fragranceService.retrieveFragrance(savedFragrance.getId());

        assertThat(retrievedFragrance.getId()).isEqualTo(savedFragrance.getId());
    }

}
