package com.bepyr.ecomarket.controller;

import com.bepyr.ecomarket.model.Resena;
import com.bepyr.ecomarket.service.ResenaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resenas")
@Tag(name = "Controlador de Reseñas", description = "API REST para la gestión de reseñas de productos")
public class ResenaController {

    @Autowired
    private ResenaService service;

    @PostMapping
    @Operation(
        summary = "Crear nueva reseña",
        description = "Registra una nueva reseña para un producto",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Reseña creada exitosamente",
                content = @Content(schema = @Schema(implementation = Resena.class))),
            @ApiResponse(
                responseCode = "400",
                description = "Datos de la reseña inválidos",
                content = @Content)
        })
    public Resena crear(
        @Parameter(description = "Objeto Reseña a crear", required = true,
                  content = @Content(schema = @Schema(implementation = Resena.class)))
        @RequestBody Resena resena) {
        return service.guardarResena(resena);
    }

    @GetMapping
    @Operation(
        summary = "Listar todas las reseñas",
        description = "Obtiene todas las reseñas disponibles en el sistema",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Lista de reseñas obtenida correctamente",
                content = @Content(schema = @Schema(implementation = Resena[].class)))
        })
    public List<Resena> listar() {
        return service.obtenerTodas();
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Obtener reseña por ID",
        description = "Recupera una reseña específica según su identificador único",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Reseña encontrada",
                content = @Content(schema = @Schema(implementation = Resena.class))),
            @ApiResponse(
                responseCode = "404",
                description = "Reseña no encontrada",
                content = @Content)
        })
    public Resena obtener(
        @Parameter(description = "ID único de la reseña", example = "1", required = true)
        @PathVariable Long id) {
        return service.obtenerPorId(id);
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Actualizar reseña",
        description = "Modifica los datos de una reseña existente",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Reseña actualizada exitosamente",
                content = @Content(schema = @Schema(implementation = Resena.class))),
            @ApiResponse(
                responseCode = "404",
                description = "Reseña no encontrada para actualizar",
                content = @Content),
            @ApiResponse(
                responseCode = "400",
                description = "Datos de actualización inválidos",
                content = @Content)
        })
    public Resena actualizar(
        @Parameter(description = "ID de la reseña a actualizar", example = "1", required = true)
        @PathVariable Long id,
        @Parameter(description = "Datos actualizados de la reseña", required = true,
                  content = @Content(schema = @Schema(implementation = Resena.class)))
        @RequestBody Resena resena) {
        return service.actualizar(id, resena);
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Eliminar reseña",
        description = "Elimina permanentemente una reseña del sistema",
        responses = {
            @ApiResponse(
                responseCode = "204",
                description = "Reseña eliminada exitosamente"),
            @ApiResponse(
                responseCode = "404",
                description = "Reseña no encontrada para eliminar")
        })
    public void eliminar(
        @Parameter(description = "ID de la reseña a eliminar", example = "1", required = true)
        @PathVariable Long id) {
        service.eliminar(id);
    }
}