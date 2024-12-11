package com.nuevo.proyecto;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import com.nuevo.proyecto.controller.LoginController;
import com.nuevo.proyecto.model.Login;
import com.nuevo.proyecto.model.Usuario;
import com.nuevo.proyecto.model.UsuarioDTO;
import com.nuevo.proyecto.service.UsuarioService;

class LoginControllerTest {

    @InjectMocks
    private LoginController loginController;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @SuppressWarnings("deprecation")
    @Test
    void testLoginSuccess() {
        // Configurar datos de prueba
        Login login = new Login();
        login.setNombre("usuarioPrueba");
        login.setPassword("password123");

        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("usuarioPrueba");
        usuario.setPassword("password123");
        usuario.setRol("user");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(usuarioService.obtenerUsuarioPorNombre("usuarioPrueba"))
                .thenReturn(Optional.of(usuario));

        // Ejecutar método
        ResponseEntity<?> response = loginController.login(login);

        // Verificar resultados
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody() instanceof Map);

        @SuppressWarnings("unchecked")
        Map<String, Object> responseBody = (Map<String, Object>) response.getBody();
        assertEquals("Inicio de sesión exitoso", responseBody.get("message"));

        UsuarioDTO usuarioDTO = (UsuarioDTO) responseBody.get("usuario");
        assertEquals(usuario.getId(), usuarioDTO.getId());
        assertEquals(usuario.getNombre(), usuarioDTO.getNombre());
        assertEquals(usuario.getRol(), usuarioDTO.getRol());
    }

    @SuppressWarnings("deprecation")
    @Test
    void testLoginBadCredentials() {
        // Configurar datos de prueba
        Login login = new Login();
        login.setNombre("usuarioPrueba");
        login.setPassword("passwordIncorrecto");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        // Ejecutar método
        ResponseEntity<?> response = loginController.login(login);

        // Verificar resultados
        assertNotNull(response);
        assertEquals(401, response.getStatusCodeValue());
        assertEquals("Credenciales incorrectas", response.getBody());
    }


    @SuppressWarnings("deprecation")
    @Test
    void testLoginErrorInterno() {
        // Configurar datos de prueba
        Login login = new Login();
        login.setNombre("usuarioPrueba");
        login.setPassword("password123");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new RuntimeException("Error interno"));

        // Ejecutar método
        ResponseEntity<?> response = loginController.login(login);

        // Verificar resultados
        assertNotNull(response);
        assertEquals(500, response.getStatusCodeValue());
        assertEquals("Error en el servidor", response.getBody());
    }
}