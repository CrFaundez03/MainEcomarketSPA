package com.bepyr.ecomarket.service;

import com.bepyr.ecomarket.model.Producto;
import java.util.List;

public interface ProductoService {
    Producto guardarProducto(Producto producto);
    List<Producto> obtenerTodos();
    Producto obtenerPorId(Long id);
    Producto actualizar(Long id, Producto producto);
    void eliminar(Long id);
}