package com.nuevo.proyecto;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.nuevo.proyecto.model.Usuario;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UsuarioTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidUsuario() {
        Usuario usuario = new Usuario();
        usuario.setNombre("Juan Perez");
        usuario.setPassword("securePass");
        usuario.setRol("user");

        Set<ConstraintViolation<Usuario>> violations = validator.validate(usuario);
        assertTrue(violations.isEmpty(), "No debería haber violaciones para un usuario válido");
    }

    @Test
    void testNombreNotNullOrBlank() {
        Usuario usuario = new Usuario();
        usuario.setNombre("");
        usuario.setPassword("securePass");
        usuario.setRol("user");

        Set<ConstraintViolation<Usuario>> violations = validator.validate(usuario);
        assertFalse(violations.isEmpty(), "Debe haber violaciones para un nombre vacío");

        ConstraintViolation<Usuario> violation = violations.iterator().next();
        assertEquals("No puede ingresar un user vacio", violation.getMessage());
    }

    @Test
    void testPasswordMinimumLength() {
        Usuario usuario = new Usuario();
        usuario.setNombre("Juan Perez");
        usuario.setPassword("123");
        usuario.setRol("user");

        Set<ConstraintViolation<Usuario>> violations = validator.validate(usuario);
        assertFalse(violations.isEmpty(), "Debe haber violaciones para una contraseña demasiado corta");

        ConstraintViolation<Usuario> violation = violations.iterator().next();
        assertEquals("La contraseña debe tener al menos 4 caracteres", violation.getMessage());
    }

    @Test
    void testRolValidValues() {
        Usuario usuario = new Usuario();
        usuario.setNombre("Juan Perez");
        usuario.setPassword("securePass");
        usuario.setRol("manager"); // Valor inválido

        Set<ConstraintViolation<Usuario>> violations = validator.validate(usuario);
        assertFalse(violations.isEmpty(), "Debe haber violaciones para un rol no válido");

        ConstraintViolation<Usuario> violation = violations.iterator().next();
        assertEquals("El rol debe ser 'user' o 'admin'", violation.getMessage());
    }


}
