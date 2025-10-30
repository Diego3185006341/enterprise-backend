package com.enterprise_backend.Contoller;

import com.enterprise_backend.Entity.Empresa;
import com.enterprise_backend.Service.EmpresaService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empresas")
@CrossOrigin(origins = {
        "http://localhost:5173",
        "https://enterprise-front-tau.vercel.app",
        "https://enterprise-front-git-master-diegos-projects-f8b66fa6.vercel.app",
        "https://enterprise-front-n8n5ig5ao-diegos-projects-f8b66fa6.vercel.app"
}, allowCredentials = "true")
public class EmpresaController {

    private final EmpresaService service;

    public EmpresaController(EmpresaService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public List<Empresa> listar() {
        return service.listar();
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Empresa crear(@RequestBody Empresa empresa) {
        return service.crear(empresa);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void eliminar(@PathVariable Long id) {
         service.eliminar(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Empresa actualizar(@RequestBody Empresa empresa) {
        return service.actualizar(empresa);
    }

    @GetMapping("/{id}")
    public Empresa findById(@PathVariable String id) {
        return service.findById(id);
    }
}
