package com.nuevo.proyecto;


import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.nuevo.proyecto.exception.GlobalExceptionHandler;
import com.nuevo.proyecto.exception.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

    @Test
    void handleValidationExceptions() {
        // Simular un error de validación
        FieldError fieldError = new FieldError("usuarioDTO", "nombre", "El nombre es obligatorio");
        
        // Crear un BindingResult simulado
        BindingResult bindingResult = mock(BindingResult.class);
        List<ObjectError> objectErrors = new ArrayList<>();
        objectErrors.add(fieldError);
        
        // Configurar el BindingResult para devolver los errores
        when(bindingResult.getAllErrors()).thenReturn(objectErrors);
        
        // Simular el MethodArgumentNotValidException
        MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
        when(exception.getBindingResult()).thenReturn(bindingResult);

        // Llamar al método de manejo de excepciones
        ResponseEntity<Map<String, String>> response = globalExceptionHandler.handleValidationExceptions(exception);

        // Verificar el resultado
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Map<String, String> erroresEsperados = new HashMap<>();
        erroresEsperados.put("nombre", "El nombre es obligatorio");
        assertEquals(erroresEsperados, response.getBody());
    }

    @Test
    void handleResourceNotFoundException() {
        // Simular una excepción de recurso no encontrado
        ResourceNotFoundException exception = new ResourceNotFoundException("El recurso no fue encontrado");

        // Llamar al método de manejo de excepciones
        ResponseEntity<String> response = globalExceptionHandler.handleResourceNotFoundException(exception);

        // Verificar el resultado
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("El recurso no fue encontrado", response.getBody());
    }

    @Test
    void handleGlobalException() {
        // Simular una excepción genérica
        Exception exception = new Exception("Error inesperado");

        // Llamar al método de manejo de excepciones
        ResponseEntity<String> response = globalExceptionHandler.handleGlobalException(exception);

        // Verificar el resultado
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Error interno en el servidor", response.getBody());
    }
}