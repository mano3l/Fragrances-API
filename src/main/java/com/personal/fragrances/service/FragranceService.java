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
        return this.fragranceRepository.findById(id).orElseThrow(NoSuchElementException::new);
    }

    public Fragrance saveFragrance(Fragrance fragrance) {
        return this.fragranceRepository.save(fragrance);
    }

    public Fragrance updateFragrance(UUID id, Fragrance fragrance) throws NoSuchElementException{
        Fragrance existingFragrance = this.fragranceRepository.findById(id).orElseThrow(NoSuchElementException::new);
        existingFragrance.setName(fragrance.getName());
        return this.fragranceRepository.save(existingFragrance);
    }

    public void deleteFragrance(UUID id) {
        this.fragranceRepository.deleteById(id);
    }
}
