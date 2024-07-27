package com.personal.fragrances.dto;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

public record FragranceDto(@NotBlank String name) implements Serializable {
}