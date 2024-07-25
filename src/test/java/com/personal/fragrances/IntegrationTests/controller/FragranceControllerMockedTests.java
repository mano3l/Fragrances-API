package com.personal.fragrances.IntegrationTests.controller;

import com.personal.fragrances.controller.FragranceController;
import com.personal.fragrances.mapper.FragranceMapper;
import com.personal.fragrances.service.FragranceService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.NoSuchElementException;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FragranceController.class)
class FragranceControllerMockedTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FragranceService fragranceService;

    @MockBean
    private FragranceMapper fragranceMapper;

    @Test
    @DisplayName("NoSuchElementExceptionThrown - return fragrance not found")
    void testHandleNoSuchElementException() throws Exception {
        UUID id = UUID.randomUUID();
        when(fragranceService.retrieveFragrance(any(UUID.class)))
                .thenThrow(new NoSuchElementException("Fragrance not found"));

        mockMvc.perform(get("/api/fragrance/" + id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Fragrance not found."));
    }

    @Test
    @DisplayName("Generic exception thrown - return unexpected error")
    void testHandleGenericException() throws Exception {
        UUID id = UUID.randomUUID();
        when(fragranceService.retrieveFragrance(any(UUID.class)))
                .thenThrow(new RuntimeException("Some unexpected error"));

        mockMvc.perform(get("/api/fragrance/" + id))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("An unexpected error occurred."));
    }
}