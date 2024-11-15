package com.nuevo.proyecto.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.nuevo.proyecto.model.Usuario;




public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

        Optional<Usuario> findByNombre(String nombre);
} 
