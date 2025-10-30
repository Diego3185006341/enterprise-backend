package com.enterprise_backend.service;

import com.enterprise_backend.Entity.Producto;
import com.enterprise_backend.Repository.ProductoRepository;
import com.enterprise_backend.Service.ProductoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductoServiceTest {

    @Mock
    private ProductoRepository repository;

    @InjectMocks
    private ProductoService service;

    @Test
    void listar_deberiaRetornarTodosLosProductos() {
        
        Producto p1 = new Producto();
        p1.setId(1L);
        p1.setNombre("Laptop");

        Producto p2 = new Producto();
        p2.setId(2L);
        p2.setNombre("Mouse");

        List<Producto> productos = Arrays.asList(p1, p2);
        when(repository.findAll()).thenReturn(productos);

        
        List<Producto> resultado = service.listar();

        
        assertThat(resultado).hasSize(2);
        assertThat(resultado).containsExactly(p1, p2);
        verify(repository).findAll();
    }

    @Test
    void crear_deberiaGuardarYRetornarElProducto() {
        
        Producto productoEntrada = new Producto();
        productoEntrada.setNombre("Teclado");
        productoEntrada.setPrecio(new BigDecimal("29.99"));

        Producto productoGuardado = new Producto();
        productoGuardado.setId(1L);
        productoGuardado.setNombre("Teclado");
        productoGuardado.setPrecio(new BigDecimal("29.99"));

        when(repository.save(productoEntrada)).thenReturn(productoGuardado);

        
        Producto resultado = service.crear(productoEntrada);

        
        assertThat(resultado.getId()).isEqualTo(1L);
        assertThat(resultado.getNombre()).isEqualTo("Teclado");
        assertThat(resultado.getPrecio()).isEqualByComparingTo(new BigDecimal("29.99")); // ✅ Correcto
        verify(repository).save(productoEntrada);
    }
}