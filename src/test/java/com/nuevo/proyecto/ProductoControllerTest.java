package com.nuevo.proyecto;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import com.nuevo.proyecto.model.Producto;
import com.nuevo.proyecto.service.ProductoService;
import com.nuevo.proyecto.controller.ProductoController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

public class ProductoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProductoService productoService;

    @InjectMocks
    private ProductoController productoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        mockMvc = MockMvcBuilders.standaloneSetup(productoController).build();  // Configuramos MockMvc para que use el controlador
    }

    // Test para listar productos
    @Test
    void testListarProductos() throws Exception {
        Producto producto1 = new Producto();
        producto1.setId(1L);
        producto1.setNombre("Producto 1");
        producto1.setPrecio(100.0);
        producto1.setDescripcion("Descripción del producto 1");

        Producto producto2 = new Producto();
        producto2.setId(2L);
        producto2.setNombre("Producto 2");
        producto2.setPrecio(200.0);
        producto2.setDescripcion("Descripción del producto 2");

        when(productoService.listarProductos()).thenReturn(Arrays.asList(producto1, producto2));

        mockMvc.perform(get("/api/productos"))  // Aquí usamos 'get' de MockMvcRequestBuilders
                .andExpect(status().isOk())  // Verificamos que el status sea 200 OK
                .andExpect(jsonPath("$.size()").value(2))  // Comprobamos que haya 2 productos
                .andExpect(jsonPath("$[0].nombre").value("Producto 1"))  // Verificamos el nombre del primer producto
                .andExpect(jsonPath("$[1].nombre").value("Producto 2"));  // Verificamos el nombre del segundo producto

        verify(productoService, times(1)).listarProductos();  // Verificamos que el servicio fue llamado correctamente
    }

    // Test para crear un nuevo producto
    @Test
    void testCrearProducto() throws Exception {
        Producto producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Producto nuevo");
        producto.setPrecio(50.0);
        producto.setDescripcion("Descripción del producto nuevo");

        when(productoService.guardaProducto(any(Producto.class))).thenReturn(producto);

        mockMvc.perform(post("/api/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\":\"Producto nuevo\", \"precio\":50.0, \"descripcion\":\"Descripción del producto nuevo\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Producto nuevo"))
                .andExpect(jsonPath("$.precio").value(50.0));

        verify(productoService, times(1)).guardaProducto(any(Producto.class));
    }

    // Test para obtener un producto por ID
    @Test
    void testObtenerProducto() throws Exception {
        Producto producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Producto 1");
        producto.setPrecio(100.0);
        producto.setDescripcion("Descripción del producto 1");

        when(productoService.obtenerProducto(1L)).thenReturn(Optional.of(producto));

        mockMvc.perform(get("/api/productos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Producto 1"))
                .andExpect(jsonPath("$.precio").value(100.0));

        verify(productoService, times(1)).obtenerProducto(1L);
    }



    // Test para actualizar un producto
    @Test
    void testActualizarProducto() throws Exception {
        Producto productoExistente = new Producto();
        productoExistente.setId(1L);
        productoExistente.setNombre("Producto 1");
        productoExistente.setPrecio(100.0);
        productoExistente.setDescripcion("Descripción del producto 1");

        Producto productoActualizado = new Producto();
        productoActualizado.setId(1L);
        productoActualizado.setNombre("Producto 1 actualizado");
        productoActualizado.setPrecio(120.0);
        productoActualizado.setDescripcion("Descripción actualizada");

        when(productoService.obtenerProducto(1L)).thenReturn(Optional.of(productoExistente));
        when(productoService.guardaProducto(any(Producto.class))).thenReturn(productoActualizado);

        mockMvc.perform(put("/api/productos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\":\"Producto 1 actualizado\", \"precio\":120.0, \"descripcion\":\"Descripción actualizada\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Producto 1 actualizado"))
                .andExpect(jsonPath("$.precio").value(120.0));

        verify(productoService, times(1)).obtenerProducto(1L);
        verify(productoService, times(1)).guardaProducto(any(Producto.class));
    }

    // Test para eliminar un producto
    @Test
    void testEliminarProducto() throws Exception {
        Producto producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Producto 1");
        producto.setPrecio(100.0);
        producto.setDescripcion("Descripción del producto 1");

        when(productoService.obtenerProducto(1L)).thenReturn(Optional.of(producto));

        mockMvc.perform(delete("/api/productos/1"))
                .andExpect(status().isNoContent());

        verify(productoService, times(1)).eliminarProducto(1L);
    }


}