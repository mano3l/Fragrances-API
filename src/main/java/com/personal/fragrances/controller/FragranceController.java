package com.personal.fragrances.controller;

import com.personal.fragrances.domain.Fragrance;
import com.personal.fragrances.dto.FragranceDto;
import com.personal.fragrances.mapper.FragranceMapper;
import com.personal.fragrances.service.FragranceService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("api")
public class FragranceController {

    private final FragranceService fragranceService;
    private final FragranceMapper fragranceMapper;

    public FragranceController(FragranceService fragranceService, FragranceMapper fragranceMapper) {
        this.fragranceService = fragranceService;
        this.fragranceMapper = fragranceMapper;
    }

    @GetMapping("fragrance/{id}")
    public ResponseEntity<FragranceDto> fragrance(@PathVariable UUID id) {
        Fragrance fragrance = this.fragranceService.retrieveFragrance(id);
        FragranceDto fragranceDto = fragranceMapper.toDto(fragrance);
        return new ResponseEntity<>(fragranceDto, HttpStatus.OK);
    }

    @PostMapping("fragrance")
    public ResponseEntity<FragranceDto> save(@Valid @RequestBody FragranceDto fragranceDto) {
        Fragrance fragrance = fragranceMapper.toEntity(fragranceDto);
        Fragrance savedFragrance = this.fragranceService.saveFragrance(fragrance);
        FragranceDto savedFragranceDto = fragranceMapper.toDto(savedFragrance);
        return new ResponseEntity<>(savedFragranceDto, HttpStatus.CREATED);
    }

    @PutMapping("fragrance/{id}")
    public ResponseEntity<FragranceDto> update(@PathVariable UUID id, @Valid @RequestBody FragranceDto fragranceDto) {
        Fragrance fragrance = fragranceMapper.toEntity(fragranceDto);
        Fragrance updatedFragrance = this.fragranceService.updateFragrance(id, fragrance);
        FragranceDto updatedFragranceDto = fragranceMapper.toDto(updatedFragrance);
        return new ResponseEntity<>(updatedFragranceDto, HttpStatus.OK);
    }

    @DeleteMapping("fragrance/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        this.fragranceService.deleteFragrance(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
