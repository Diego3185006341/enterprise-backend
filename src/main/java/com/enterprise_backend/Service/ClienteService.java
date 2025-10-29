package com.enterprise_backend.Service;

import com.enterprise_backend.Entity.Cliente;
import com.enterprise_backend.Repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ClienteService {
    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    public List<Cliente> listar() {
        return repository.findAll();
    }

    public Cliente crear(Cliente cliente) {
        return repository.save(cliente);
    }  public void delete(Long clienteId) {
         repository.deleteById(clienteId);
    }
}
