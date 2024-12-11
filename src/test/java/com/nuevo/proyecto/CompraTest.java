package com.nuevo.proyecto;

import org.junit.jupiter.api.Test;

import com.nuevo.proyecto.model.Compra;

import static org.junit.jupiter.api.Assertions.*;

class CompraTest {

    @Test
    void testCompraGettersAndSetters() {
        Compra compra = new Compra();

        // Establecer valores
        compra.setId(1L);
        compra.setProductoId(101L);
        compra.setCantidad(5);
        compra.setUsuarioId(202L);

        // Verificar valores
        assertEquals(1L, compra.getId(), "El ID debería ser 1");
        assertEquals(101L, compra.getProductoId(), "El productoId debería ser 101");
        assertEquals(5, compra.getCantidad(), "La cantidad debería ser 5");
        assertEquals(202L, compra.getUsuarioId(), "El usuarioId debería ser 202");
    }

    @Test
    void testCompraDefaultValues() {
        Compra compra = new Compra();

        // Verificar valores predeterminados
        assertNull(compra.getId(), "El ID debería ser null por defecto");
        assertNull(compra.getProductoId(), "El productoId debería ser null por defecto");
        assertEquals(0, compra.getCantidad(), "La cantidad debería ser 0 por defecto");
        assertNull(compra.getUsuarioId(), "El usuarioId debería ser null por defecto");
    }

    @Test
    void testCompraEquality() {
        Compra compra1 = new Compra();
        compra1.setProductoId(101L);
        compra1.setCantidad(5);
        compra1.setUsuarioId(202L);

        Compra compra2 = new Compra();
        compra2.setProductoId(101L);
        compra2.setCantidad(5);
        compra2.setUsuarioId(202L);

        // Verificar que son objetos diferentes pero con los mismos datos
        assertNotSame(compra1, compra2, "Los objetos Compra deben ser diferentes");
        assertEquals(compra1.getProductoId(), compra2.getProductoId(), "Los productoId deben ser iguales");
        assertEquals(compra1.getCantidad(), compra2.getCantidad(), "Las cantidades deben ser iguales");
        assertEquals(compra1.getUsuarioId(), compra2.getUsuarioId(), "Los usuarioId deben ser iguales");
    }
}