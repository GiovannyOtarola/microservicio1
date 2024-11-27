package com.nuevo.proyecto.model;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class ProductoDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private Double precio;
    private Long usuarioId; 
}