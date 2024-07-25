package com.personal.fragrances.service;

import com.personal.fragrances.domain.Fragrance;
import com.personal.fragrances.repository.FragranceRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class FragranceService {

    private final FragranceRepository fragranceRepository;

    public FragranceService(FragranceRepository fragranceRepository) {
        this.fragranceRepository = fragranceRepository;
    }

    public Fragrance retrieveFragrance(UUID id) throws NoSuchElementException{
        return this.fragranceRepository.findById(id).orElseThrow(() -> new NoSuchElementException("No fragrance found"));
    }

    public Fragrance saveFragrance(Fragrance fragrance) {
        return this.fragranceRepository.save(fragrance);
    }
}
