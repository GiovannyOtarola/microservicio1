package com.nuevo.proyecto;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertTrue;

@SpringBootTest
public class AppTest {
    @Test
    public void testApp() {
        // Prueba que se ejecuta correctamente
        assertTrue(true);
    }

    @Test
    public void contextLoads() {
        // Verifica que el contexto de la aplicación se carga sin errores.
        // Si el contexto no se carga correctamente, Spring lanzará una excepción.
    }

    
}
