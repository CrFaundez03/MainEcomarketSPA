package com.bepyr.ecomarket.service;

import com.bepyr.ecomarket.model.Producto;
import com.bepyr.ecomarket.model.Resena;
import com.bepyr.ecomarket.repository.ResenaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ResenaServiceImplTest {

    @Mock
    private ResenaRepository resenaRepository;

    @InjectMocks
    private ResenaServiceImpl resenaService;

    private Resena resena;
    private Producto producto;

    @BeforeEach
    void setUp() {
        producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Laptop Gamer");

        resena = new Resena();
        resena.setId(1L);
        resena.setComentario("Excelente producto");
        resena.setCalificacion(5);
        resena.setProducto(producto);
    }

    @Test
    @DisplayName("Guardar reseña - Éxito")
    void guardarResena_DeberiaRetornarResenaGuardada() {
        // Arrange
        when(resenaRepository.save(any(Resena.class))).thenReturn(resena);

        // Act
        Resena resultado = resenaService.guardarResena(resena);

        // Assert
        assertNotNull(resultado);
        assertEquals("Excelente producto", resultado.getComentario());
        assertEquals(5, resultado.getCalificacion());
        assertNotNull(resultado.getProducto());
        verify(resenaRepository, times(1)).save(resena);
    }

    @Test
    @DisplayName("Obtener todas las reseñas - Con datos")
    void obtenerTodas_ConDatos_DeberiaRetornarLista() {
        // Arrange
        Resena resena2 = new Resena();
        resena2.setId(2L);
        resena2.setComentario("Buen producto");
        resena2.setCalificacion(4);

        when(resenaRepository.findAll()).thenReturn(Arrays.asList(resena, resena2));

        // Act
        List<Resena> resultados = resenaService.obtenerTodas();

        // Assert
        assertEquals(2, resultados.size());
        assertEquals("Buen producto", resultados.get(1).getComentario());
        verify(resenaRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Obtener todas las reseñas - Vacío")
    void obtenerTodas_SinDatos_DeberiaRetornarListaVacia() {
        // Arrange
        when(resenaRepository.findAll()).thenReturn(List.of());

        // Act
        List<Resena> resultados = resenaService.obtenerTodas();

        // Assert
        assertTrue(resultados.isEmpty());
        verify(resenaRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Obtener reseña por ID - Existente")
    void obtenerPorId_ConIdExistente_DeberiaRetornarResena() {
        // Arrange
        when(resenaRepository.findById(1L)).thenReturn(Optional.of(resena));

        // Act
        Resena resultado = resenaService.obtenerPorId(1L);

        // Assert
        assertNotNull(resultado);
        assertEquals("Excelente producto", resultado.getComentario());
        assertEquals(1L, resultado.getId());
        verify(resenaRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Obtener reseña por ID - No existente")
    void obtenerPorId_ConIdInexistente_DeberiaRetornarNull() {
        // Arrange
        when(resenaRepository.findById(1L)).thenReturn(Optional.empty());

        // Act
        Resena resultado = resenaService.obtenerPorId(1L);

        // Assert
        assertNull(resultado);
        verify(resenaRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Actualizar reseña - Éxito")
    void actualizar_DeberiaRetornarResenaActualizada() {
        // Arrange
        Resena resenaActualizada = new Resena();
        resenaActualizada.setComentario("Muy buen producto");
        resenaActualizada.setCalificacion(4);

        when(resenaRepository.save(any(Resena.class))).thenAnswer(invocation -> {
            Resena r = invocation.getArgument(0);
            r.setId(1L); // Simula el guardado
            return r;
        });

        // Act
        Resena resultado = resenaService.actualizar(1L, resenaActualizada);

        // Assert
        assertEquals(1L, resultado.getId());
        assertEquals("Muy buen producto", resultado.getComentario());
        assertEquals(4, resultado.getCalificacion());
        verify(resenaRepository, times(1)).save(resenaActualizada);
    }

    @Test
    @DisplayName("Eliminar reseña - Éxito")
    void eliminar_DeberiaLlamarAlRepositorio() {
        // Arrange
        doNothing().when(resenaRepository).deleteById(1L);

        // Act
        resenaService.eliminar(1L);

        // Assert
        verify(resenaRepository, times(1)).deleteById(1L);
    }
}