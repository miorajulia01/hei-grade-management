package com.example.demo.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;

class GlobalExceptionHandlerTest {

  private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

  @Test
  void shouldReturnConflictForDatabaseConstraintViolation() {
    HttpServletRequest request = request("/academic-years");

    var response =
        handler.handleDataIntegrityViolation(
            new DataIntegrityViolationException("constraint"), request);

    assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals("/academic-years", response.getBody().getPath());
  }

  @Test
  void shouldReturnServerErrorForUnexpectedException() {
    HttpServletRequest request = request("/programs");

    var response = handler.handleUnexpectedException(new RuntimeException("failure"), request);

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals("/programs", response.getBody().getPath());
  }

  private MockHttpServletRequest request(String path) {
    MockHttpServletRequest request = new MockHttpServletRequest();
    request.setRequestURI(path);
    return request;
  }
}
