package com.nuevo.proyecto;


import com.nuevo.proyecto.model.Compra;
import com.nuevo.proyecto.repository.CompraRepository;
import com.nuevo.proyecto.service.CompraService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CompraServiceTest {

    @Mock
    private CompraRepository compraRepository;

    @InjectMocks
    private CompraService compraService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRealizarCompra() {
        // Crear una compra de ejemplo
        Compra compra = new Compra();
        compra.setId(1L);
        compra.setProductoId(101L);
        compra.setCantidad(5);
        compra.setUsuarioId(202L);

        // Configurar el comportamiento simulado del repositorio
        when(compraRepository.save(compra)).thenReturn(compra);

        // Llamar al método del servicio
        Compra resultado = compraService.realizarCompra(compra);

        // Verificar el comportamiento
        assertNotNull(resultado, "El resultado no debería ser nulo");
        assertEquals(1L, resultado.getId(), "El ID debería ser 1");
        assertEquals(101L, resultado.getProductoId(), "El productoId debería ser 101");
        assertEquals(5, resultado.getCantidad(), "La cantidad debería ser 5");
        assertEquals(202L, resultado.getUsuarioId(), "El usuarioId debería ser 202");

        // Verificar que el repositorio fue llamado una vez
        verify(compraRepository, times(1)).save(compra);
    }
}