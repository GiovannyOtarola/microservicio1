package com.nuevo.proyecto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nuevo.proyecto.repository.CompraRepository;
import com.nuevo.proyecto.model.Compra;

@Service
public class CompraService {

    @Autowired
    private CompraRepository compraRepository;

    public Compra realizarCompra(Compra compra) {
        return compraRepository.save(compra);
    }

    
}
