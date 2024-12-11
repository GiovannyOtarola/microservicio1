package com.nuevo.proyecto;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.nuevo.proyecto.model.Producto;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ProductoTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidProducto() {
        Producto producto = new Producto();
        producto.setNombre("Producto válido");
        producto.setDescripcion("Descripción válida");
        producto.setPrecio(100.0);

        Set<ConstraintViolation<Producto>> violations = validator.validate(producto);
        assertTrue(violations.isEmpty(), "No debería haber violaciones para un producto válido");
    }

    @Test
    void testNombreNotBlank() {
        Producto producto = new Producto();
        producto.setNombre(""); // Nombre vacío
        producto.setDescripcion("Descripción válida");
        producto.setPrecio(100.0);

        Set<ConstraintViolation<Producto>> violations = validator.validate(producto);
        assertFalse(violations.isEmpty(), "Debe haber violaciones para un nombre vacío");

        ConstraintViolation<Producto> violation = violations.iterator().next();
        assertEquals("El nombre no puede estar en blanco", violation.getMessage());
    }

    @Test
    void testNombreMaxLength() {
        Producto producto = new Producto();
        producto.setNombre("A".repeat(101)); // Nombre de 101 caracteres
        producto.setDescripcion("Descripción válida");
        producto.setPrecio(100.0);

        Set<ConstraintViolation<Producto>> violations = validator.validate(producto);
        assertFalse(violations.isEmpty(), "Debe haber violaciones para un nombre demasiado largo");

        ConstraintViolation<Producto> violation = violations.iterator().next();
        assertEquals("El nombre no puede tener más de 100 caracteres", violation.getMessage());
    }

    @Test
    void testDescripcionMaxLength() {
        Producto producto = new Producto();
        producto.setNombre("Producto válido");
        producto.setDescripcion("A".repeat(256)); // Descripción de 256 caracteres
        producto.setPrecio(100.0);

        Set<ConstraintViolation<Producto>> violations = validator.validate(producto);
        assertFalse(violations.isEmpty(), "Debe haber violaciones para una descripción demasiado larga");

        ConstraintViolation<Producto> violation = violations.iterator().next();
        assertEquals("La descripción no puede tener más de 255 caracteres", violation.getMessage());
    }

    @Test
    void testPrecioNotNull() {
        Producto producto = new Producto();
        producto.setNombre("Producto válido");
        producto.setDescripcion("Descripción válida");
        producto.setPrecio(null); // Precio nulo

        Set<ConstraintViolation<Producto>> violations = validator.validate(producto);
        assertFalse(violations.isEmpty(), "Debe haber violaciones para un precio nulo");

        ConstraintViolation<Producto> violation = violations.iterator().next();
        assertEquals("El precio no puede ser nulo", violation.getMessage());
    }

    @Test
    void testPrecioGreaterThanZero() {
        Producto producto = new Producto();
        producto.setNombre("Producto válido");
        producto.setDescripcion("Descripción válida");
        producto.setPrecio(0.0); // Precio no válido (0)

        Set<ConstraintViolation<Producto>> violations = validator.validate(producto);
        assertFalse(violations.isEmpty(), "Debe haber violaciones para un precio menor o igual a 0");

        ConstraintViolation<Producto> violation = violations.iterator().next();
        assertEquals("El precio debe ser mayor que 0", violation.getMessage());
    }
}