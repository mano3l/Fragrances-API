package com.personal.fragrances.IntegrationTests.controller;

import com.personal.fragrances.controller.FragranceController;
import com.personal.fragrances.mapper.FragranceMapper;
import com.personal.fragrances.service.FragranceService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.NoSuchElementException;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FragranceController.class)
class FragranceControllerMockedTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FragranceService fragranceService;

    @MockBean
    private FragranceMapper fragranceMapper;

    @Autowired
    private FragranceController fragranceController;

    @Test
    @DisplayName("NoSuchElementExceptionThrown - return fragrance not found")
    void testHandleNoSuchElementException() throws Exception {
        UUID id = UUID.randomUUID();
        when(fragranceService.retrieveFragrance(any(UUID.class)))
                .thenThrow(new NoSuchElementException());

        mockMvc.perform(get("/api/fragrance/" + id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Resource not found"))
                .andExpect(jsonPath("$.errorCode").value("RESOURCE_NOT_FOUND"));
    }

    @Test
    @DisplayName("POST Fragrance - Returns Bad Request Status")
    void testHandleValidationExceptions() throws Exception {
        mockMvc.perform(post("/api/fragrance")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.errorCode").value("VALIDATION_FAILED"));
    }

    @Test
    @DisplayName("Generic exception thrown - return unexpected error")
    void testHandleGenericException() throws Exception {
        UUID id = UUID.randomUUID();
        when(fragranceService.retrieveFragrance(any(UUID.class)))
                .thenThrow(new RuntimeException());

        mockMvc.perform(get("/api/fragrance/" + id))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("An unexpected error occurred"))
                .andExpect(jsonPath("$.errorCode").value("INTERNAL_SERVER_ERROR"));
    }

    @Test
    @DisplayName("IllegalArgumentExceptionThrown - return invalid argument")
    void testHandleIllegalArgumentException() throws Exception {
        UUID id = UUID.randomUUID();
        when(fragranceService.retrieveFragrance(any(UUID.class)))
                .thenThrow(new IllegalArgumentException("Invalid argument"));

        mockMvc.perform(get("/api/fragrance/" + id))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Invalid argument"))
                .andExpect(jsonPath("$.errorCode").value("INVALID_ARGUMENT"));
    }

}