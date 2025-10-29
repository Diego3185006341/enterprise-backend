package com.enterprise_backend.Contoller;

import com.enterprise_backend.Entity.Empresa;
import com.enterprise_backend.Entity.Producto;
import com.enterprise_backend.Service.EmpresaService;
import com.enterprise_backend.Service.ProductoService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins ={"http://localhost:5173",
        "https://enterprise-front-production.up.railway.app"})
public class ProductoController {
    private final ProductoService service;


    public ProductoController(ProductoService service) {
        this.service = service;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<Producto> listar() {
        return service.listar();
    }

    @PostMapping
    public Producto crear(@RequestBody Producto producto) {
        return service.crear(producto);
    }
}
