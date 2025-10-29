package com.enterprise_backend.Service;

import com.enterprise_backend.Entity.Categoria;
import com.enterprise_backend.Repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CategoriaService {
    private final CategoriaRepository repository;

    public CategoriaService(CategoriaRepository repository) {
        this.repository = repository;
    }

    public List<Categoria> listar() {
        return repository.findAll();
    }

    public Categoria crear(Categoria categoria) {
        return repository.save(categoria);
    }
}
