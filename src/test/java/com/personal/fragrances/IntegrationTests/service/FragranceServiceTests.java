package com.personal.fragrances.IntegrationTests.service;

import com.personal.fragrances.TestSecurityConfig;
import com.personal.fragrances.domain.Fragrance;
import com.personal.fragrances.repository.FragranceRepository;
import com.personal.fragrances.service.FragranceService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.NoSuchElementException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Import(TestSecurityConfig.class)
@Testcontainers
@ActiveProfiles("test")
class FragranceServiceTests {

    @Autowired
    private FragranceService fragranceService;

    @Autowired
    private FragranceRepository fragranceRepository;

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest");

    @AfterEach
    void cleanUp() {
        fragranceRepository.deleteAll();
    }

    @Test
    @DisplayName("Retrieve a fragrance")
    @Sql(scripts = "classpath:scripts/insert_fragrance.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void shouldRetrieveFragrance() {
        // Given
        UUID id = UUID.fromString("cd7534fa-6dab-4fa5-9411-102fc2ccf0bf");
        // When
        Fragrance fragrance = fragranceService.retrieveFragrance(id);
        // Then
        assertThat(fragrance).isNotNull();
    }

    @Test
    @DisplayName("Throw exception when fragrance not found")
    void shouldThrowExceptionWhenFragranceNotFound() {
        // Given
        UUID id = UUID.fromString("cd7534fa-6dab-4fa5-9411-102fc2ccf0bf");
        // When
        // Then
        assertThatThrownBy(() -> fragranceService.retrieveFragrance(id))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("Save fragrance on database")
    void shouldSaveFragrance() {
        // Given
        Fragrance fragrance = new Fragrance();
        fragrance.setName("Lavanda");
        // When
        Fragrance savedFragrance = fragranceService.saveFragrance(fragrance);
        // Then
        assertThat(savedFragrance).isNotNull();
        assertThat(savedFragrance.getId()).isNotNull();
        assertThat(savedFragrance.getName()).isEqualTo("Lavanda");
    }

    @Test
    @DisplayName("Update fragrance on database")
    @Sql(scripts = "classpath:scripts/insert_fragrance.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void shouldUpdateFragrance() {
        // Given
        UUID id = UUID.fromString("cd7534fa-6dab-4fa5-9411-102fc2ccf0bf");
        Fragrance fragrance = new Fragrance();
        fragrance.setName("Lavanda");
        // When
        Fragrance updatedFragrance = fragranceService.updateFragrance(id, fragrance);
        // Then
        assertThat(updatedFragrance).isNotNull();
        assertThat(updatedFragrance.getId()).isEqualTo(id);
        assertThat(updatedFragrance.getName()).isEqualTo("Lavanda");
    }

    @Test
    @DisplayName("Delete fragrance from database")
    @Sql(scripts = "classpath:scripts/insert_fragrance.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void shouldDeleteFragrance() {
        // Given
        UUID id = UUID.fromString("cd7534fa-6dab-4fa5-9411-102fc2ccf0bf");
        // When
        fragranceService.deleteFragrance(id);
        // Then
        assertThat(fragranceRepository.findById(id)).isEmpty();
    }

}
