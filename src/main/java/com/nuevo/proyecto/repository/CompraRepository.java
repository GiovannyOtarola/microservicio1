package com.nuevo.proyecto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.nuevo.proyecto.model.Compra;

public interface CompraRepository extends JpaRepository<Compra, Long> {
    // Métodos personalizados si es necesario
}
