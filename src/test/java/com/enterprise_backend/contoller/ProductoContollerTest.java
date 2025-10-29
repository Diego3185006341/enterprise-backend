package com.enterprise_backend.contoller;

import com.enterprise_backend.Contoller.ProductoController;
import com.enterprise_backend.Entity.Producto;
import com.enterprise_backend.Service.ProductoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductoControllerTest {

    @Mock
    private ProductoService service;

    @InjectMocks
    private ProductoController controller;

    @Test
    void listar_deberiaDelegarEnElServicio() {
        // Given
        Producto p1 = new Producto();
        p1.setId(1L);
        p1.setNombre("Laptop");

        Producto p2 = new Producto();
        p2.setId(2L);
        p2.setNombre("Mouse");

        List<Producto> expected = Arrays.asList(p1, p2);
        when(service.listar()).thenReturn(expected);

        // When
        List<Producto> result = controller.listar();

        // Then
        assertThat(result).isEqualTo(expected);
        verify(service).listar();
    }

    @Test
    void crear_deberiaDelegarEnElServicio() {
        // Given
        Producto input = new Producto();
        input.setNombre("Teclado");

        Producto saved = new Producto();
        saved.setId(1L);
        saved.setNombre("Teclado");

        when(service.crear(input)).thenReturn(saved);

        // When
        Producto result = controller.crear(input);

        // Then
        assertThat(result).isEqualTo(saved);
        verify(service).crear(input);
    }
}