package com.personal.fragrances.controller;

import com.personal.fragrances.domain.Fragrance;
import com.personal.fragrances.dto.FragranceDto;
import com.personal.fragrances.mapper.FragranceMapper;
import com.personal.fragrances.service.FragranceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        Fragrance fragrance;
        try {
            fragrance = this.fragranceService.retrieveFragrance(id);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            throw exception;
        }
        FragranceDto fragranceDto = fragranceMapper.toDto(fragrance);
        return new ResponseEntity<>(fragranceDto, HttpStatus.OK);
    }
}
