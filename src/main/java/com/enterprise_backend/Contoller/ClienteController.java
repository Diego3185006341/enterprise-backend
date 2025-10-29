package com.enterprise_backend.Contoller;

import com.enterprise_backend.Entity.Cliente;
import com.enterprise_backend.Service.ClienteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin
public class ClienteController {
    private final ClienteService service;


    public ClienteController(ClienteService service) {
        this.service = service;
    }

    @GetMapping
    public List<Cliente> listar() {
        return service.listar();
    }

    @PostMapping
    public Cliente crear(@RequestBody Cliente cliente) {
        return service.crear(cliente);
    }
    @DeleteMapping("/{id}")
    public void deleteAllClientes(@PathVariable Long id) {
        service.delete(id);
    }
}
