package com.nuevo.proyecto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nuevo.proyecto.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long>{
    
}