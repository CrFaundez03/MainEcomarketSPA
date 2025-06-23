package com.bepyr.ecomarket.service;

import com.bepyr.ecomarket.model.Resena;
import com.bepyr.ecomarket.repository.ResenaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResenaServiceImpl implements ResenaService {

    @Autowired
    private ResenaRepository repo;

    @Override
    public Resena guardarResena(Resena resena) {
        return repo.save(resena);
    }

    @Override
    public List<Resena> obtenerTodas() {
        return repo.findAll();
    }

    @Override
    public Resena obtenerPorId(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Resena actualizar(Long id, Resena resena) {
        resena.setId(id);
        return repo.save(resena);
    }

    @Override
    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}