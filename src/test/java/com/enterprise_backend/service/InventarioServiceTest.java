package com.enterprise_backend.service;

import com.enterprise_backend.Entity.Empresa;
import com.enterprise_backend.Entity.Producto;
import com.enterprise_backend.Repository.ProductoRepository;
import com.enterprise_backend.Service.InventarioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InventarioServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private InventarioService service;

    @Test
    void generarPDFOpen_deberiaGenerarPDFConDatosValidos() throws Exception {

        Empresa empresa = new Empresa();
        empresa.setNombre("Empresa A");

        Producto p1 = new Producto();
        p1.setNombre("Laptop");
        p1.setCaracteristicas("16GB RAM, SSD 512GB");
        p1.setPrecio(new BigDecimal("1200.00"));
        p1.setCodigo("LAP-001");
        p1.setEmpresa(empresa);

        Producto p2 = new Producto();
        p2.setNombre("Mouse");
        p2.setCaracteristicas("Inalámbrico");
        p2.setPrecio(new BigDecimal("25.99"));
        p2.setCodigo("MSE-002");
        p2.setEmpresa(empresa);

        when(productoRepository.findAll()).thenReturn(Arrays.asList(p1, p2));


        byte[] pdfBytes = service.generarPDFOpen();


        assertThat(pdfBytes).isNotNull();
        assertThat(pdfBytes).isNotEmpty();

    }

    @Test
    void generarPDFOpen_deberiaLanzarExcepcion_cuandoRepositorioFalla() throws Exception {

        when(productoRepository.findAll()).thenThrow(new RuntimeException("DB error"));


        assertThatThrownBy(() -> service.generarPDFOpen())
                .isInstanceOf(Exception.class);
    }
}