package com.enterprise_backend.contoller;

import com.enterprise_backend.Contoller.InventarioController;
import com.enterprise_backend.Service.EmailService;
import com.enterprise_backend.Service.InventarioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventarioControllerTest {

    @Mock
    private InventarioService inventarioService;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private InventarioController controller;

    // === descargarPDF() tests ===

    @Test
    void descargarPDF_deberiaRetornarPDF_cuandoGeneracionEsExitosa() throws Exception {
        byte[] pdfData = new byte[]{1, 2, 3, 4}; // PDF simulado
        when(inventarioService.generarPDFOpen()).thenReturn(pdfData);

        ResponseEntity<byte[]> response = controller.descargarPDF();

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(pdfData);
        assertThat(response.getHeaders().getContentDisposition().getFilename()).isEqualTo("inventario.pdf");
        assertThat(response.getHeaders().getContentType().toString()).contains("application/pdf");
    }

    @Test
    void descargarPDF_deberiaRetornar500_cuandoFallanGeneracion() throws Exception {
        when(inventarioService.generarPDFOpen()).thenThrow(new RuntimeException("PDF error"));

        ResponseEntity<byte[]> response = controller.descargarPDF();

        assertThat(response.getStatusCode().value()).isEqualTo(500);
        assertThat(response.getBody()).isNull();
    }

    // === enviarPDF() tests ===

    @Test
    void enviarPDF_deberiaRetornar200_cuandoCorreoSeEnviaCorrectamente() throws Exception {
        String destinatario = "admin@empresa.com";

        ResponseEntity<String> response = controller.enviarPDF(destinatario);

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo("Correo enviado correctamente");
        verify(emailService).enviarPDF(destinatario);
    }

    @Test
    void enviarPDF_deberiaRetornar500_cuandoFallanEnvio() throws Exception {
        String destinatario = "admin@empresa.com";
        doThrow(new RuntimeException("Email failed")).when(emailService).enviarPDF(destinatario);

        ResponseEntity<String> response = controller.enviarPDF(destinatario);

        assertThat(response.getStatusCode().value()).isEqualTo(500);
        assertThat(response.getBody()).isEqualTo("Error al enviar el correo");
    }
}