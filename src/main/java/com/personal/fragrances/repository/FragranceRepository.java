package com.personal.fragrances.repository;

import com.personal.fragrances.domain.Fragrance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FragranceRepository extends JpaRepository<Fragrance, UUID> {
}