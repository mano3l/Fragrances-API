package com.personal.fragrances.service;

import com.personal.fragrances.domain.Fragrance;
import com.personal.fragrances.repository.FragranceRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FragranceService {

    private final FragranceRepository fragranceRepository;

    public FragranceService(FragranceRepository fragranceRepository) {
        this.fragranceRepository = fragranceRepository;
    }

    public Fragrance retrieveFragrance(UUID id) {
        return this.fragranceRepository.findById(id).orElseThrow(() -> new RuntimeException("No fragrance found"));
    }
}
