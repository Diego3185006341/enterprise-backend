package com.enterprise_backend.Contoller;

import com.enterprise_backend.Service.EmailService;
import com.enterprise_backend.Service.InventarioService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.File;
@AllArgsConstructor
@RestController
@RequestMapping("/api/inventario")
@CrossOrigin(origins = "http://localhost:5173")
public class InventarioController {

    @Autowired
    private InventarioService inventarioService;

    private final EmailService emailService;

    @GetMapping("/pdf")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<byte[]> descargarPDF() {
        try {
            byte[] pdf = inventarioService.generarPDFOpen(); // 👈 aquí se llama el service

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=inventario.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdf);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @PostMapping("/email-pdf")
    public ResponseEntity<String> enviarPDF(@RequestParam String destinatario) {
        try {
            // Genera o apunta al archivo
            emailService.enviarPDF(destinatario);
            return ResponseEntity.ok("Correo enviado correctamente");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al enviar el correo");
        }
    }

}

