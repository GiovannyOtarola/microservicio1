package com.nuevo.proyecto.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.nuevo.proyecto.exception.ResourceNotFoundException;
import com.nuevo.proyecto.model.Login;
import com.nuevo.proyecto.model.Usuario;
import com.nuevo.proyecto.model.UsuarioDTO;
import com.nuevo.proyecto.service.UsuarioService;

@RestController
@RequestMapping("/api/login")
@CrossOrigin(origins = "http://localhost:4200")
public class LoginController {
    
     @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody Login login) {
        try {
            // Realiza la autenticación
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    login.getNombre(),
                    login.getPassword()
                )
            );

            // Establece el contexto de seguridad
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Obtener el usuario autenticado
            Usuario usuario = usuarioService.obtenerUsuarioPorNombre(login.getNombre())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

            // Crear un objeto UsuarioDTO
            UsuarioDTO usuarioDTO = new UsuarioDTO();
            usuarioDTO.setId(usuario.getId());
            usuarioDTO.setNombre(usuario.getNombre());
            usuarioDTO.setPassword(usuario.getPassword());
            usuarioDTO.setRol(usuario.getRol());

            // Crear un objeto de respuesta
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Inicio de sesión exitoso");
            response.put("usuario", usuarioDTO); // Aquí se incluye el DTO del usuario

            // Devuelve respuesta exitosa
            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            // Manejo de credenciales incorrectas
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
        } catch (Exception e) {
            // Manejo de otros errores
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error en el servidor");
        }
    }
}
