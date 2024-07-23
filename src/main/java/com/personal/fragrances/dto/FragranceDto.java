package com.personal.fragrances.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.personal.fragrances.domain.Fragrance}
 */
@Value
public class FragranceDto implements Serializable {
    String name;
}