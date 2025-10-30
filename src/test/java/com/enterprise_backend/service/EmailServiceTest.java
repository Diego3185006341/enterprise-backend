package com.enterprise_backend.service;

import com.enterprise_backend.Service.EmailService;
import com.enterprise_backend.Service.InventarioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @Mock
    private InventarioService inventarioService;

    @InjectMocks
    private EmailService emailService;

    @Test
    void enviarPDF_deberiaLlamarAGenerarPDFYNoLanzarExcepcion() throws Exception {

        String destinatario = "cliente@empresa.com";
        byte[] pdfFalso = new byte[]{37, 80, 68, 70};
        when(inventarioService.generarPDFOpen()).thenReturn(pdfFalso);


        emailService.enviarPDF(destinatario);

        // Then
        verify(inventarioService).generarPDFOpen();

    }

    @Test
    void enviarPDF_deberiaLanzarExcepcion_cuandoGeneracionPDFFallA() throws Exception {

        String destinatario = "cliente@empresa.com";
        when(inventarioService.generarPDFOpen()).thenThrow(new RuntimeException("PDF error"));


        assertThrows(Exception.class, () -> {
            emailService.enviarPDF(destinatario);
        });

        verify(inventarioService).generarPDFOpen();
    }
}