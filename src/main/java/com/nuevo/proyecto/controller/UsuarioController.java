package com.nuevo.proyecto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nuevo.proyecto.exception.ResourceNotFoundException;
import com.nuevo.proyecto.model.Usuario;
import com.nuevo.proyecto.model.UsuarioDTO;
import com.nuevo.proyecto.service.UsuarioService;

import jakarta.validation.Valid;

import java.util.List;


@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "http://localhost:4200")
public class UsuarioController {


    @Autowired
    private UsuarioService usuarioService;
    
   

    // Crear un nuevo usuario
    @PostMapping
    public ResponseEntity<Usuario> crearUsuario(@RequestBody Usuario usuario) {
        Usuario nuevoUsuario = usuarioService.crearUsuario(usuario);
        return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED);
    }

    // Obtener usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuario(@PathVariable Long id) {
        Usuario usuario = usuarioService.obtenerUsuarioPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException("El usuario con ID " + id + " no fue encontrado."));
        return ResponseEntity.ok(usuario);
    }

    // Actualizar usuario
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Long id, @Valid @RequestBody Usuario usuarioDetalles) {
        Usuario usuario = usuarioService.obtenerUsuarioPorId(id)
        .orElseThrow(() -> new ResourceNotFoundException("El libro con ID " + id + " no fue encontrado."));

            usuario.setNombre(usuarioDetalles.getNombre());
            usuario.setPassword(usuarioDetalles.getPassword());
            usuario.setRol(usuarioDetalles.getRol());

            Usuario usuarioActualizado = usuarioService.guardUsuario(usuario);
            return ResponseEntity.ok(usuarioActualizado);
    }

    // Eliminar usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        usuarioService.obtenerUsuarioPorId(id)
                .orElseThrow(() -> new ResourceNotFoundException("El usuario con ID " + id + " no fue encontrado."));

        usuarioService.eliminarUsuario(id);

        return ResponseEntity.noContent().build(); // Devolver 204 No Content
    }

    // Obtener todos los usuarios 
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> obtenerTodosLosUsuarios() {
        List<UsuarioDTO> usuariosDTO = usuarioService.obtenerTodosLosUsuarios();
        return ResponseEntity.ok(usuariosDTO);
    }


    // Obtener usuario por nombre
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<Usuario> obtenerUsuarioPorNombre(@PathVariable String nombre) {
        Usuario usuario = usuarioService.obtenerUsuarioPorNombre(nombre)
                .orElseThrow(() -> new ResourceNotFoundException("El usuario con nombre " + nombre + " no fue encontrado."));
        return ResponseEntity.ok(usuario);
    }

}
