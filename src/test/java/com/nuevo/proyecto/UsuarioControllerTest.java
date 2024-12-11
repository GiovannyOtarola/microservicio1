package com.nuevo.proyecto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;

import org.springframework.test.web.servlet.MockMvc;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.nuevo.proyecto.controller.UsuarioController;
import com.nuevo.proyecto.exception.ResourceNotFoundException;
import com.nuevo.proyecto.model.Usuario;
import com.nuevo.proyecto.model.UsuarioDTO;
import com.nuevo.proyecto.service.UsuarioService;

@WebMvcTest(UsuarioController.class)
public class UsuarioControllerTest {

    @InjectMocks
    private UsuarioController usuarioController;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        usuario = new Usuario();
    }

    @Test
    void crearUsuario() {
        when(usuarioService.crearUsuario(any(Usuario.class))).thenReturn(usuario);

        ResponseEntity<Usuario> response = usuarioController.crearUsuario(usuario);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(usuario, response.getBody());
        verify(usuarioService, times(1)).crearUsuario(any(Usuario.class));
    }

    @Test
    void obtenerUsuario() {
        when(usuarioService.obtenerUsuarioPorId(1L)).thenReturn(Optional.of(usuario));

        ResponseEntity<Usuario> response = usuarioController.obtenerUsuario(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(usuario, response.getBody());
    }

    @Test
    void obtenerUsuario_NotFound() {
        when(usuarioService.obtenerUsuarioPorId(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            usuarioController.obtenerUsuario(1L);
        });

        assertEquals("El usuario con ID 1 no fue encontrado.", exception.getMessage());
    }

    @Test
    void actualizarUsuario() {
        Usuario usuarioDetalles = new Usuario();
        when(usuarioService.obtenerUsuarioPorId(1L)).thenReturn(Optional.of(usuario));
        when(usuarioService.guardUsuario(any(Usuario.class))).thenReturn(usuarioDetalles);

        ResponseEntity<Usuario> response = usuarioController.actualizarUsuario(1L, usuarioDetalles);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(usuarioDetalles, response.getBody());
        verify(usuarioService, times(1)).guardUsuario(any(Usuario.class));
    }

    @Test
    void eliminarUsuario() {
        when(usuarioService.obtenerUsuarioPorId(1L)).thenReturn(Optional.of(usuario));

        ResponseEntity<Void> response = usuarioController.eliminarUsuario(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(usuarioService, times(1)).eliminarUsuario(1L);
    }

    @Test
    void obtenerTodosLosUsuarios() {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        List<UsuarioDTO> usuariosDTO = Arrays.asList(usuarioDTO);
        when(usuarioService.obtenerTodosLosUsuarios()).thenReturn(usuariosDTO);

        ResponseEntity<List<UsuarioDTO>> response = usuarioController.obtenerTodosLosUsuarios();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(usuariosDTO, response.getBody());
    }

    @Test
    void obtenerUsuarioPorNombre() {
        when(usuarioService.obtenerUsuarioPorNombre("nombre")).thenReturn(Optional.of(usuario));

        ResponseEntity<Usuario> response = usuarioController.obtenerUsuarioPorNombre("nombre");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(usuario, response.getBody());
    }   

    @Test
    void obtenerUsuarioPorNombre_NotFound() {
        when(usuarioService.obtenerUsuarioPorNombre("nombre")).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            usuarioController.obtenerUsuarioPorNombre("nombre");
        });

        assertEquals("El usuario con nombre nombre no fue encontrado.", exception.getMessage());
    }



}
