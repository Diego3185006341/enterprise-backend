package com.enterprise_backend.Service;

import com.enterprise_backend.Entity.Producto;
import com.enterprise_backend.Repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductoService {
    private final ProductoRepository repository;

    public ProductoService(ProductoRepository repository) {
        this.repository = repository;
    }

    public List<Producto> listar() {
        return repository.findAll();
    }

    public Producto crear(Producto producto) {
        return repository.save(producto);
    }
}
