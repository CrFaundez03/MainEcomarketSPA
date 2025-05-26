package com.bepyr.ecomarket.service;

import com.bepyr.ecomarket.model.Producto;
import com.bepyr.ecomarket.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository repo;

    @Override
    public Producto guardarProducto(Producto producto) {
        return repo.save(producto);
    }

    @Override
    public List<Producto> obtenerTodos() {
        return repo.findAll();
    }

    @Override
    public Producto obtenerPorId(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Producto actualizar(Long id, Producto productoActualizado) {
        Producto existente = repo.findById(id)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        existente.setNombre(productoActualizado.getNombre());
        existente.setDescripcion(productoActualizado.getDescripcion());
        existente.setPrecio(productoActualizado.getPrecio());

        return repo.save(existente);
    }


    @Override
    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}