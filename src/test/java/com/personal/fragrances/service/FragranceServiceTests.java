package com.personal.fragrances.service;

import com.personal.fragrances.domain.Fragrance;
import com.personal.fragrances.repository.FragranceRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
@Import(TestcontainersConfiguration.class)
@ActiveProfiles("test")
public class FragranceServiceTests {

    @Autowired
    private FragranceRepository fragranceRepository;

    @Autowired
    private FragranceService fragranceService;

    @Test
    void shouldRetrieveFragrance() {
        Fragrance savedFragrance = fragranceRepository.save(new Fragrance("Eros"));

        Fragrance retrievedFragrance = fragranceService.retrieveFragrance(savedFragrance.getId());

        assertThat(retrievedFragrance.getId()).isEqualTo(savedFragrance.getId());
    }

}
