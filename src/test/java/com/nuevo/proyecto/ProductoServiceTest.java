package com.nuevo.proyecto;


import com.nuevo.proyecto.model.Producto;
import com.nuevo.proyecto.repository.ProductoRepository;
import com.nuevo.proyecto.service.ProductoService;

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

class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListarProductos() {
        // Configurar datos simulados
        Producto producto1 = new Producto();
        producto1.setId(1L);
        producto1.setNombre("Producto 1");

        Producto producto2 = new Producto();
        producto2.setId(2L);
        producto2.setNombre("Producto 2");

        when(productoRepository.findAll()).thenReturn(Arrays.asList(producto1, producto2));

        // Llamar al método del servicio
        List<Producto> productos = productoService.listarProductos();

        // Verificar resultados
        assertNotNull(productos, "La lista de productos no debería ser nula");
        assertEquals(2, productos.size(), "Debería haber 2 productos");
        assertEquals("Producto 1", productos.get(0).getNombre(), "El primer producto debería ser 'Producto 1'");

        // Verificar interacción con el repositorio
        verify(productoRepository, times(1)).findAll();
    }

    @Test
    void testObtenerProducto() {
        // Configurar datos simulados
        Producto producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Producto 1");

        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));

        // Llamar al método del servicio
        Optional<Producto> resultado = productoService.obtenerProducto(1L);

        // Verificar resultados
        assertTrue(resultado.isPresent(), "El producto debería estar presente");
        assertEquals("Producto 1", resultado.get().getNombre(), "El nombre del producto debería ser 'Producto 1'");

        // Verificar interacción con el repositorio
        verify(productoRepository, times(1)).findById(1L);
    }

    @Test
    void testObtenerProductoNoEncontrado() {
        // Configurar datos simulados
        when(productoRepository.findById(1L)).thenReturn(Optional.empty());

        // Llamar al método del servicio
        Optional<Producto> resultado = productoService.obtenerProducto(1L);

        // Verificar resultados
        assertFalse(resultado.isPresent(), "El producto no debería estar presente");

        // Verificar interacción con el repositorio
        verify(productoRepository, times(1)).findById(1L);
    }

    @Test
    void testGuardarProducto() {
        // Configurar datos simulados
        Producto producto = new Producto();
        producto.setNombre("Producto Guardado");

        when(productoRepository.save(producto)).thenReturn(producto);

        // Llamar al método del servicio
        Producto resultado = productoService.guardaProducto(producto);

        // Verificar resultados
        assertNotNull(resultado, "El producto guardado no debería ser nulo");
        assertEquals("Producto Guardado", resultado.getNombre(), "El nombre del producto debería ser 'Producto Guardado'");

        // Verificar interacción con el repositorio
        verify(productoRepository, times(1)).save(producto);
    }

    @Test
    void testEliminarProducto() {
        // Llamar al método del servicio
        productoService.eliminarProducto(1L);

        // Verificar interacción con el repositorio
        verify(productoRepository, times(1)).deleteById(1L);
    }
}