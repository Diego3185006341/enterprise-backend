package com.enterprise_backend.Service;

import com.enterprise_backend.Entity.Empresa;
import com.enterprise_backend.Repository.EmpresaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpresaService {
    private final EmpresaRepository repository;

    public EmpresaService(EmpresaRepository repository) {
        this.repository = repository;
    }

    public List<Empresa> listar() {
        return repository.findAll();
    }

    public Empresa crear(Empresa empresa) {
        return repository.save(empresa);
    }

    public void eliminar(Long id) {
       repository.deleteById(String.valueOf(id));
    }
    public Empresa actualizar(Empresa empresaToUpdate) {
        Empresa empresa = repository.findById(empresaToUpdate.getNit()).orElse(Empresa.builder().build());
        empresa.setNit(empresaToUpdate.getNit());
        empresa.setNombre(empresaToUpdate.getNombre());
        empresa.setDireccion(empresaToUpdate.getDireccion());
        empresa.setTelefono(empresaToUpdate.getTelefono());
        return repository.save(empresa);
    }

    public Empresa findById(String id) {
        return repository.findById(id).orElse(Empresa.builder().build());
    }
}