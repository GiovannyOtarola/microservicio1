package com.nuevo.proyecto;


import org.junit.jupiter.api.Test;

import com.nuevo.proyecto.model.Login;

import static org.junit.jupiter.api.Assertions.*;

class LoginTest {

    @Test
    void testLoginGettersAndSetters() {
        Login login = new Login();

        // Establecer valores
        login.setNombre("usuarioPrueba");
        login.setPassword("password123");

        // Verificar valores
        assertEquals("usuarioPrueba", login.getNombre(), "El nombre debería ser 'usuarioPrueba'");
        assertEquals("password123", login.getPassword(), "El password debería ser 'password123'");
    }

    @Test
    void testLoginDefaultValues() {
        Login login = new Login();

        // Verificar valores predeterminados
        assertNull(login.getNombre(), "El nombre debería ser null por defecto");
        assertNull(login.getPassword(), "El password debería ser null por defecto");
    }

    @Test
    void testLoginEquality() {
        Login login1 = new Login();
        login1.setNombre("usuarioPrueba");
        login1.setPassword("password123");

        Login login2 = new Login();
        login2.setNombre("usuarioPrueba");
        login2.setPassword("password123");

        // Verificar igualdad (hashCode y equals no están implementados explícitamente)
        assertNotSame(login1, login2, "Los objetos deben ser diferentes");
        assertEquals(login1.getNombre(), login2.getNombre(), "Los nombres deben ser iguales");
        assertEquals(login1.getPassword(), login2.getPassword(), "Los passwords deben ser iguales");
    }
}