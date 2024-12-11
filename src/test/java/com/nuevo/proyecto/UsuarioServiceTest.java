package com.nuevo.proyecto;


import com.nuevo.proyecto.model.Usuario;
import com.nuevo.proyecto.model.UsuarioDTO;
import com.nuevo.proyecto.repository.UsuarioRepository;
import com.nuevo.proyecto.service.UsuarioService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearUsuario() {
        // Configurar datos simulados
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Test User");
        usuario.setPassword("password");
        usuario.setRol("admin");

        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        // Llamar al método del servicio
        Usuario resultado = usuarioService.crearUsuario(usuario);

        // Verificar resultados
        assertNotNull(resultado, "El usuario creado no debería ser nulo");
        assertEquals("Test User", resultado.getNombre(), "El nombre del usuario debería ser 'Test User'");
        assertEquals("admin", resultado.getRol(), "El rol del usuario debería ser 'admin'");

        // Verificar interacción con el repositorio
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    void testObtenerUsuarioPorId() {
        // Configurar datos simulados
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Test User");

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        // Llamar al método del servicio
        Optional<Usuario> resultado = usuarioService.obtenerUsuarioPorId(1L);

        // Verificar resultados
        assertTrue(resultado.isPresent(), "El usuario debería estar presente");
        assertEquals("Test User", resultado.get().getNombre(), "El nombre del usuario debería ser 'Test User'");

        // Verificar interacción con el repositorio
        verify(usuarioRepository, times(1)).findById(1L);
    }

    @Test
    void testObtenerUsuarioPorNombre() {
        // Configurar datos simulados
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Test User");

        when(usuarioRepository.findByNombre("Test User")).thenReturn(Optional.of(usuario));

        // Llamar al método del servicio
        Optional<Usuario> resultado = usuarioService.obtenerUsuarioPorNombre("Test User");

        // Verificar resultados
        assertTrue(resultado.isPresent(), "El usuario debería estar presente");
        assertEquals("Test User", resultado.get().getNombre(), "El nombre del usuario debería ser 'Test User'");

        // Verificar interacción con el repositorio
        verify(usuarioRepository, times(1)).findByNombre("Test User");
    }

    @Test
    void testObtenerTodosLosUsuarios() {
        // Configurar datos simulados
        Usuario usuario1 = new Usuario();
        usuario1.setId(1L);
        usuario1.setNombre("User1");
        usuario1.setPassword("password1");
        usuario1.setRol("admin");

        Usuario usuario2 = new Usuario();
        usuario2.setId(2L);
        usuario2.setNombre("User2");
        usuario2.setPassword("password2");
        usuario2.setRol("user");

        when(usuarioRepository.findAll()).thenReturn(Arrays.asList(usuario1, usuario2));

        // Llamar al método del servicio
        List<UsuarioDTO> usuarios = usuarioService.obtenerTodosLosUsuarios();

        // Verificar resultados
        assertNotNull(usuarios, "La lista de usuarios no debería ser nula");
        assertEquals(2, usuarios.size(), "Debería haber 2 usuarios");
        assertEquals("User1", usuarios.get(0).getNombre(), "El primer usuario debería ser 'User1'");
        assertEquals("User2", usuarios.get(1).getNombre(), "El segundo usuario debería ser 'User2'");

        // Verificar interacción con el repositorio
        verify(usuarioRepository, times(1)).findAll();
    }

    @Test
    void testEliminarUsuario() {
        // Llamar al método del servicio
        usuarioService.eliminarUsuario(1L);

        // Verificar interacción con el repositorio
        verify(usuarioRepository, times(1)).deleteById(1L);
    }

    @Test
    void testGuardUsuario() {
        // Configurar datos simulados
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Nuevo Usuario");
        usuario.setPassword("nuevaPassword");
        usuario.setRol("user");

        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        // Llamar al método del servicio
        Usuario resultado = usuarioService.guardUsuario(usuario);

        // Verificar resultados
        assertNotNull(resultado, "El usuario guardado no debería ser nulo");
        assertEquals("Nuevo Usuario", resultado.getNombre(), "El nombre del usuario guardado debería ser 'Nuevo Usuario'");
        assertEquals("user", resultado.getRol(), "El rol del usuario guardado debería ser 'user'");

        // Verificar interacción con el repositorio
        verify(usuarioRepository, times(1)).save(usuario);
    }
}
