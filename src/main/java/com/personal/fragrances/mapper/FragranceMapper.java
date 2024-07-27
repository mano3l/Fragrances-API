package com.personal.fragrances.mapper;

import com.personal.fragrances.domain.Fragrance;
import com.personal.fragrances.dto.FragranceDto;
import org.springframework.stereotype.Component;

@Component
public class FragranceMapper {

    public FragranceDto toDto(Fragrance fragrance) {
        FragranceDto dto = new FragranceDto(
                fragrance.getName()
        );
        return dto;
    }

    public Fragrance toEntity(FragranceDto fragranceDto) {
        Fragrance entity = new Fragrance(
                fragranceDto.name()
        );
        return entity;
    }

}
