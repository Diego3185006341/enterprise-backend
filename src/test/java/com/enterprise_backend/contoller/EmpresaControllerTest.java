package com.enterprise_backend.contoller;
import com.enterprise_backend.Contoller.EmpresaController;
import com.enterprise_backend.Entity.Empresa;
import com.enterprise_backend.Service.EmpresaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class EmpresaControllerUnitTest {

    @Mock
    private EmpresaService service;

    @InjectMocks
    private EmpresaController controller;

    @Test
    void listar_deberiaRetornarTodasLasEmpresas() {
        // Given
        Empresa emp1 = new Empresa();
        emp1.setNit("1");
        emp1.setNombre("Empresa A");

        Empresa emp2 = new Empresa();
        emp2.setNit("2");
        emp2.setNombre("Empresa B");

        List<Empresa> lista = Arrays.asList(emp1, emp2);
        when(service.listar()).thenReturn(lista);

        // When
        List<Empresa> resultado = controller.listar();

        // Then
        assertThat(resultado).hasSize(2);
        assertThat(resultado.get(0).getNombre()).isEqualTo("Empresa A");
    }

    @Test
    void crear_deberiaDelegarEnElServicio() {
        // Given
        Empresa entrada = new Empresa();
        entrada.setNombre("Nueva");

        Empresa guardada = new Empresa();
        guardada.setNit("1");
        guardada.setNombre("Nueva");
        when(service.crear(entrada)).thenReturn(guardada);

        // When
        Empresa resultado = controller.crear(entrada);

        // Then
        assertThat(resultado.getNit()).isEqualTo("1");
        assertThat(resultado.getNombre()).isEqualTo("Nueva");
    }
}