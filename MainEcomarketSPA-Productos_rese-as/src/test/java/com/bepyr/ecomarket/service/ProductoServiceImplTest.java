package com.bepyr.ecomarket.service;

import com.bepyr.ecomarket.model.Producto;
import com.bepyr.ecomarket.model.Resena;
import com.bepyr.ecomarket.repository.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductoServiceImplTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoServiceImpl productoService;

    private Producto producto;
    private Producto productoConResenas;

    @BeforeEach
    void setUp() {
        // Producto básico
        producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Laptop");
        producto.setDescripcion("Laptop Gamer");
        producto.setPrecio(950000);
        producto.setCantidad(10);

        // Producto con reseñas (para probar la relación OneToMany)
        Resena resena = new Resena();
        resena.setId(1L);
        resena.setComentario("Excelente producto");

        productoConResenas = new Producto();
        productoConResenas.setId(2L);
        productoConResenas.setNombre("Mouse");
        productoConResenas.setResenas(Collections.singletonList(resena));
    }

    @Test
    void guardarProducto_DeberiaRetornarProductoConId() {
        when(productoRepository.save(any(Producto.class))).thenReturn(producto);

        Producto resultado = productoService.guardarProducto(producto);

        assertNotNull(resultado.getId());
        assertEquals("Laptop", resultado.getNombre());
        verify(productoRepository, times(1)).save(producto);
    }

    @Test
    void obtenerPorId_ConResenas_DeberiaIncluirResenas() {
        when(productoRepository.findById(2L)).thenReturn(Optional.of(productoConResenas));

        Producto resultado = productoService.obtenerPorId(2L);

        assertFalse(resultado.getResenas().isEmpty());
        assertEquals("Excelente producto", resultado.getResenas().get(0).getComentario());
    }

    @Test
    void actualizar_DeberiaModificarCantidad() {
        Producto actualizado = new Producto();
        actualizado.setCantidad(5);

        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));
        when(productoRepository.save(any(Producto.class))).thenReturn(producto);

        Producto resultado = productoService.actualizar(1L, actualizado);

        assertEquals(5, resultado.getCantidad());
    }

    
}