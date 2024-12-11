package com.nuevo.proyecto;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;



import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

        @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
    }

    @Test
    void testPermitirAccesoPublicoAUsuarios() throws Exception {
        mockMvc.perform(get("/api/usuarios"))
                .andExpect(status().isUnauthorized()); // Se espera 401 si no está autenticado
    }

    @Test
    void testAccederAPostUsuariosSinAutenticacion() throws Exception {
        mockMvc.perform(get("/api/usuarios"))
                .andExpect(status().isUnauthorized()); // 401 si no está autenticado
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testAccesoPermitidoAUsuariosConRolAdmin() throws Exception {
        // Aquí puedes simular un usuario con rol admin para verificar que el acceso es permitido
        mockMvc.perform(get("/api/usuarios/1")
                .header("Authorization", "Bearer fake-jwt-token-admin")) // Ejemplo de autenticación simulada
                .andExpect(status().isOk()); // Debería permitir el acceso con rol admin
    }


    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testAccesoPermitidoAPostUsuariosConRolAdmin() throws Exception {
        // Simulando acceso de un usuario con rol 'admin'
        mockMvc.perform(get("/api/usuarios/{id}", 1)
                .header("Authorization", "Bearer fake-jwt-token-admin"))
                .andExpect(status().isOk()); // Debería permitir el acceso con el rol adecuado
    }


}