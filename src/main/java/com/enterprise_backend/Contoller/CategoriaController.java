package com.enterprise_backend.Contoller;

import com.enterprise_backend.Entity.Categoria;
import com.enterprise_backend.Entity.Producto;
import com.enterprise_backend.Service.CategoriaService;
import com.enterprise_backend.Service.ProductoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@CrossOrigin
public class CategoriaController {

    private final CategoriaService service;


    public CategoriaController(CategoriaService service) {
        this.service = service;
    }

    @GetMapping
    public List<Categoria> listar() {
        return service.listar();
    }

    @PostMapping
    public Categoria crear(@RequestBody Categoria categoria) {
        return service.crear(categoria);
    }
}

