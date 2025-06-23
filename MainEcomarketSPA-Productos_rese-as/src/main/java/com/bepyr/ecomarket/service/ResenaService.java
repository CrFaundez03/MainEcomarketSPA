package com.bepyr.ecomarket.service;

import com.bepyr.ecomarket.model.Resena;
import java.util.List;

public interface ResenaService {
    Resena guardarResena(Resena resena);
    List<Resena> obtenerTodas();
    Resena obtenerPorId(Long id);
    Resena actualizar(Long id, Resena resena);
    void eliminar(Long id);
}