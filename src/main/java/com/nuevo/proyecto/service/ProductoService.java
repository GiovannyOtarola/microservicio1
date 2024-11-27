package com.nuevo.proyecto.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nuevo.proyecto.repository.ProductoRepository;
import com.nuevo.proyecto.model.Producto;
import com.nuevo.proyecto.model.ProductoDTO;

@Service
public class ProductoService {
    
    @Autowired
    private ProductoRepository productoRepository;

    public List<ProductoDTO> listarProductos() {
        List<Producto> productos = productoRepository.findAll();
        return productos.stream().map(producto -> {
            ProductoDTO dto = new ProductoDTO();
            dto.setId(producto.getId());
            dto.setNombre(producto.getNombre());
            dto.setDescripcion(producto.getDescripcion());
            dto.setPrecio(producto.getPrecio());
            dto.setUsuarioId(producto.getUsuario().getId()); // Solo el ID del usuario
            return dto;
        }).collect(Collectors.toList());
    }

    public Optional<Producto> obtenerProducto(Long id){
        return productoRepository.findById(id);
    }

    public Producto guardaProducto(Producto producto){
        return productoRepository.save(producto);
    }

    public void eliminarProducto(Long id){
        productoRepository.deleteById(id);
    }
}