package com.personal.fragrances.UnitTests;

import com.personal.fragrances.controller.FragranceController;
import com.personal.fragrances.domain.Fragrance;
import com.personal.fragrances.infra.auth.JwtUtil;
import com.personal.fragrances.mapper.FragranceMapper;
import com.personal.fragrances.service.FragranceService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.NoSuchElementException;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FragranceController.class)
@AutoConfigureMockMvc(addFilters = false)
class ControllerExceptionHandlerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FragranceService fragranceService;

    @MockBean
    private FragranceMapper fragranceMapper;

    @Autowired
    private FragranceController fragranceController;

    @MockBean
    private JwtUtil jwtUtil;

    @Test
    @DisplayName("NoSuchElementExceptionThrown - return fragrance not found")
    void testHandleNoSuchElementException() throws Exception {
        UUID id = UUID.randomUUID();
        when(fragranceService.retrieveFragrance(any(UUID.class)))
                .thenThrow(new NoSuchElementException());

        mockMvc.perform(get("/api/fragrance/" + id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Resource not found"));
    }

    @Test
    @DisplayName("POST Fragrance - Returns Bad Request Status")
    void testHandleValidationExceptions() throws Exception {
        mockMvc.perform(post("/api/fragrance")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation failed"));
    }

    @Test
    @DisplayName("Generic exception thrown - return unexpected error")
    void testHandleGenericException() throws Exception {
        UUID id = UUID.randomUUID();
        when(fragranceService.retrieveFragrance(any(UUID.class)))
                .thenThrow(new RuntimeException());

        mockMvc.perform(get("/api/fragrance/" + id))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("An unexpected error occurred"));
    }

    @Test
    @DisplayName("Invalid argument - throw MethodNotValidArgumentException")
    void testHandleMethodNotValidArgumentException() throws Exception {
        UUID id = UUID.randomUUID();
        when(fragranceService.updateFragrance(any(UUID.class), any(Fragrance.class)))
                .thenThrow(new RuntimeException());

        mockMvc.perform(put("/api/fragrance/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation failed"));
    }

}