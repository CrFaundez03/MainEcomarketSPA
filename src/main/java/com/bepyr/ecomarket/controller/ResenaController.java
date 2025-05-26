package com.bepyr.ecomarket.controller;

import com.bepyr.ecomarket.model.Resena;
import com.bepyr.ecomarket.service.ResenaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resenas")
public class ResenaController {

    @Autowired
    private ResenaService service;

    @PostMapping
    public Resena crear(@RequestBody Resena resena) {
        return service.guardarResena(resena);
    }

    @GetMapping
    public List<Resena> listar() {
        return service.obtenerTodas();
    }

    @GetMapping("/{id}")
    public Resena obtener(@PathVariable Long id) {
        return service.obtenerPorId(id);
    }

    @PutMapping("/{id}")
    public Resena actualizar(@PathVariable Long id, @RequestBody Resena resena) {
        return service.actualizar(id, resena);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}