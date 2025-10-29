package com.enterprise_backend.Contoller;

import com.enterprise_backend.Entity.Orden;
import com.enterprise_backend.Service.OrdenService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ordenes")
@CrossOrigin
public class OrdenController {

    private final OrdenService service;

    public OrdenController(OrdenService service) {
        this.service = service;
    }

    @GetMapping
    public List<Orden> listar() {
        return service.listar();
    }

    @PostMapping
    public Orden crear(@RequestBody Orden orden) {
        return service.crear(orden);
    }
}
