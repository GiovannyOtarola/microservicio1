package com.nuevo.proyecto.model;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class UsuarioDTO {
    private Long id;
    private String nombre;
    private String password;
    private String rol; // O cualquier otro campo que necesites
}
