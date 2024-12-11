package com.nuevo.proyecto;

import org.junit.jupiter.api.Test;

import org.springframework.security.core.AuthenticationException;

import com.nuevo.proyecto.security.CustomAuthenticationEntryPoint;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

import static org.mockito.Mockito.*;

class CustomAuthenticationEntryPointTest {

    private final CustomAuthenticationEntryPoint entryPoint = new CustomAuthenticationEntryPoint();

    @Test
    void commence_ShouldSetUnauthorizedStatusAndWriteErrorMessage() throws IOException {
        // Simular HttpServletRequest y HttpServletResponse
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        AuthenticationException authException = mock(AuthenticationException.class);

        // Simular el PrintWriter
        PrintWriter writer = mock(PrintWriter.class);
        when(response.getWriter()).thenReturn(writer);

        // Llamar al método commence
        entryPoint.commence(request, response, authException);

        // Verificar que se estableció el estado 401
        verify(response).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        // Verificar que se escribió el mensaje de error
        verify(writer).write("Error 401: No estás autenticado. Por favor, proporciona credenciales válidas.");
    }
}