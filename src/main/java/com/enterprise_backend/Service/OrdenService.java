package com.enterprise_backend.Service;

import com.enterprise_backend.Entity.Orden;
import com.enterprise_backend.Repository.OrdenRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OrdenService {

    private final OrdenRepository repository;

    public OrdenService(OrdenRepository repository) {
        this.repository = repository;
    }

    public List<Orden> listar() {
        return repository.findAll();
    }

    public Orden crear(Orden orden) {
        return repository.save(orden);
    }
}
