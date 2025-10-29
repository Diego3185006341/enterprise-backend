package com.enterprise_backend.service;
import com.enterprise_backend.Entity.Empresa;
import com.enterprise_backend.Repository.EmpresaRepository;
import com.enterprise_backend.Service.EmpresaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmpresaServiceTest {

    @Mock
    private EmpresaRepository repository;

    @InjectMocks
    private EmpresaService service;

    // === listar() ===

    @Test
    void listar_deberiaRetornarTodasLasEmpresas() {
        // Given
        Empresa e1 = Empresa.builder().nit("123").nombre("Empresa A").build();
        Empresa e2 = Empresa.builder().nit("456").nombre("Empresa B").build();
        when(repository.findAll()).thenReturn(Arrays.asList(e1, e2));

        // When
        var resultado = service.listar();

        // Then
        assertThat(resultado).hasSize(2);
        assertThat(resultado.get(0).getNit()).isEqualTo("123");
        verify(repository).findAll();
    }

    // === crear() ===

    @Test
    void crear_deberiaGuardarYRetornarLaEmpresa() {
        // Given
        Empresa entrada = Empresa.builder().nit("789").nombre("Nueva Empresa").build();
        Empresa guardada = Empresa.builder().nit("789").nombre("Nueva Empresa").build();
        when(repository.save(entrada)).thenReturn(guardada);

        // When
        Empresa resultado = service.crear(entrada);

        // Then
        assertThat(resultado).isEqualTo(guardada);
        verify(repository).save(entrada);
    }

    // === eliminar() ===

    @Test
    void eliminar_deberiaLlamarARepositoryConIdComoString() {
        // When
        service.eliminar(123L);

        // Then
        verify(repository).deleteById("123"); // ¡Convierte Long a String!
    }

    // === actualizar() ===

    @Test
    void actualizar_deberiaActualizarCamposYGuardar() {
        // Given
        String nit = "999";
        Empresa existente = Empresa.builder()
                .nit(nit)
                .nombre("Viejo Nombre")
                .direccion("Vieja Direccion")
                .telefono("111")
                .build();

        Empresa datosActualizados = Empresa.builder()
                .nit(nit)
                .nombre("Nuevo Nombre")
                .direccion("Nueva Direccion")
                .telefono("222")
                .build();

        Empresa guardada = Empresa.builder()
                .nit(nit)
                .nombre("Nuevo Nombre")
                .direccion("Nueva Direccion")
                .telefono("222")
                .build();

        when(repository.findById(nit)).thenReturn(Optional.of(existente));
        when(repository.save(any(Empresa.class))).thenReturn(guardada);

        // When
        Empresa resultado = service.actualizar(datosActualizados);

        // Then
        assertThat(resultado.getNombre()).isEqualTo("Nuevo Nombre");
        assertThat(resultado.getDireccion()).isEqualTo("Nueva Direccion");
        assertThat(resultado.getTelefono()).isEqualTo("222");
        verify(repository).findById(nit);
        verify(repository).save(any(Empresa.class));
    }

    @Test
    void actualizar_deberiaCrearNuevaEmpresaSiNoExiste() {
        // Given
        Empresa datosActualizados = Empresa.builder()
                .nit("nuevo-nit")
                .nombre("Empresa Nueva")
                .build();

        Empresa empresaVacia = Empresa.builder().build(); // default
        Empresa guardada = Empresa.builder()
                .nit("nuevo-nit")
                .nombre("Empresa Nueva")
                .build();

        when(repository.findById("nuevo-nit")).thenReturn(Optional.empty());
        when(repository.save(any(Empresa.class))).thenReturn(guardada);

        // When
        Empresa resultado = service.actualizar(datosActualizados);

        // Then
        assertThat(resultado.getNit()).isEqualTo("nuevo-nit");
        assertThat(resultado.getNombre()).isEqualTo("Empresa Nueva");
        verify(repository).findById("nuevo-nit");
        verify(repository).save(any(Empresa.class));
    }

    // === findById() ===

    @Test
    void findById_deberiaRetornarEmpresaCuandoExiste() {
        // Given
        Empresa esperada = Empresa.builder().nit("111").nombre("Buscada").build();
        when(repository.findById("111")).thenReturn(Optional.of(esperada));

        // When
        Empresa resultado = service.findById("111");

        // Then
        assertThat(resultado).isEqualTo(esperada);
    }

    @Test
    void findById_deberiaRetornarEmpresaVaciaCuandoNoExiste() {
        // Given
        when(repository.findById("no-existe")).thenReturn(Optional.empty());

        // When
        Empresa resultado = service.findById("no-existe");

        // Then
        assertThat(resultado).isEqualTo(Empresa.builder().build());
    }
}