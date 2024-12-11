package com.nuevo.proyecto;


import com.nuevo.proyecto.controller.CompraController;
import com.nuevo.proyecto.model.Compra;
import com.nuevo.proyecto.service.CompraService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class CompraControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CompraService compraService;

    @InjectMocks
    private CompraController compraController;

    @BeforeEach
    void setUp() {
        // Inicia los mocks de Mockito
        MockitoAnnotations.openMocks(this);

        // Configura MockMvc para el controlador
        mockMvc = MockMvcBuilders.standaloneSetup(compraController).build();
    }

    @Test
    void testRealizarCompra() throws Exception {
        // Crear un objeto Compra
        Compra compra = new Compra();
        compra.setId(1L);  
        compra.setProductoId(100L);  
        compra.setCantidad(2);  
        compra.setUsuarioId(10L);

        // Configurar el mock del servicio para devolver una compra
        when(compraService.realizarCompra(any(Compra.class))).thenReturn(compra);

        // Realiza la petición POST
        mockMvc.perform(post("/api/compras")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"productoId\":100, \"cantidad\":2, \"usuarioId\":10}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.productoId").value(100))
                .andExpect(jsonPath("$.cantidad").value(2))
                .andExpect(jsonPath("$.usuarioId").value(10));

        // Verifica que el método del servicio fue llamado correctamente
        verify(compraService, times(1)).realizarCompra(any(Compra.class));
    }
}