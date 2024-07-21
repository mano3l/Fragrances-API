package com.personal.fragrances.service;

import com.personal.fragrances.domain.Fragrance;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Import(TestcontainersConfiguration.class)
@ActiveProfiles("test")
public class FragranceServiceTests {

    @Autowired
    private FragranceService fragranceService;

    @Test
    @Sql(scripts = "classpath:scripts/service/insert_fragrance.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:scripts/service/delete_fragrance.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void shouldRetrieveFragrance() {
        // Given
        UUID id = UUID.fromString("cd7534fa-6dab-4fa5-9411-102fc2ccf0bf");
        // When
        Fragrance fragrance = fragranceService.retrieveFragrance(id);
        // Then
        assertThat(fragrance).isNotNull();
    }

}
