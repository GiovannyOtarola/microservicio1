package com.nuevo.proyecto;


import org.junit.jupiter.api.Test;

import com.nuevo.proyecto.model.UsuarioDTO;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioDTOTest {

    @Test
    void testUsuarioDTOGettersAndSetters() {
        UsuarioDTO usuario = new UsuarioDTO();

        // Set values
        usuario.setId(1L);
        usuario.setNombre("Juan Perez");
        usuario.setPassword("securePassword");
        usuario.setRol("ADMIN");

        // Validate values
        assertEquals(1L, usuario.getId());
        assertEquals("Juan Perez", usuario.getNombre());
        assertEquals("securePassword", usuario.getPassword());
        assertEquals("ADMIN", usuario.getRol());
    }

    @Test
    void testUsuarioDTOEquality() {
        UsuarioDTO usuario1 = new UsuarioDTO();
        usuario1.setId(1L);
        usuario1.setNombre("Juan Perez");
        usuario1.setPassword("securePassword");
        usuario1.setRol("ADMIN");

        UsuarioDTO usuario2 = new UsuarioDTO();
        usuario2.setId(1L);
        usuario2.setNombre("Juan Perez");
        usuario2.setPassword("securePassword");
        usuario2.setRol("ADMIN");


        assertNotEquals(usuario1, usuario2); 

    
    }
}