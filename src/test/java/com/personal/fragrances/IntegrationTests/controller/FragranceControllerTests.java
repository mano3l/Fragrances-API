package com.personal.fragrances.IntegrationTests.controller;

import com.personal.fragrances.repository.FragranceRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ActiveProfiles("test")
class FragranceControllerTests {

    @LocalServerPort
    private Integer port;

    @Autowired
    private FragranceRepository fragranceRepository;

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest");

    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost:" + port;
    }

    @AfterEach
    void cleanUp() {
        fragranceRepository.deleteAll();
    }

    @Test
    @DisplayName("GET Fragrance by ID - Returns Fragrance Details")
    @Sql(scripts = "classpath:scripts/insert_fragrance.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void getFragranceById_ReturnsFragranceDetails() {
        String id = "cd7534fa-6dab-4fa5-9411-102fc2ccf0bf";
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/fragrance/" + id)
                .then()
                .statusCode(200)
                .body("name", equalTo("Fresh Breeze"));
    }

    @Test
    @DisplayName("POST Fragrance - Returns Created Status")
    void postFragrance_ReturnsCreatedStatus() {
        String requestBody = "{\"name\": \"Cold Water\"}";
        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/fragrance")
                .then()
                .statusCode(201)
                .body("name", equalTo("Cold Water"));
    }

    @Test
    @DisplayName("PUT Fragrance - Returns OK Status")
    @Sql(scripts = "classpath:scripts/insert_fragrance.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void putFragrance_ReturnsOkStatus() {
        String id = "cd7534fa-6dab-4fa5-9411-102fc2ccf0bf";
        String requestBody = "{\"name\": \"Cold Water\"}";
        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .put("/api/fragrance/" + id)
                .then()
                .statusCode(200)
                .body("name", equalTo("Cold Water"));
    }

    @Test
    @DisplayName("DELETE Fragrance - Returns No Content Status")
    @Sql(scripts = "classpath:scripts/insert_fragrance.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void deleteFragrance_ReturnsNoContentStatus() {
        String id = "cd7534fa-6dab-4fa5-9411-102fc2ccf0bf";
        given()
                .contentType(ContentType.JSON)
                .when()
                .delete("/api/fragrance/" + id)
                .then()
                .statusCode(204);
    }

}
