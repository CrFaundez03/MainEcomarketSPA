package com.bepyr.ecomarket.controller;

import com.bepyr.ecomarket.model.Producto;
import com.bepyr.ecomarket.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@Tag(name = "Controlador de Productos", description = "API REST para la gestión de productos del e-commerce")
public class ProductoController {

    @Autowired
    private ProductoService service;

    @PostMapping
    @Operation(
        summary = "Crear nuevo producto",
        description = "Registra un nuevo producto en el sistema",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Producto creado exitosamente",
                content = @Content(schema = @Schema(implementation = Producto.class))),
            @ApiResponse(
                responseCode = "400",
                description = "Datos del producto inválidos",
                content = @Content)
        })
    public Producto crear(
        @Parameter(description = "Datos del producto a crear", required = true,
                  content = @Content(schema = @Schema(implementation = Producto.class)))
        @RequestBody Producto producto) {
        return service.guardarProducto(producto);
    }

    @GetMapping
    @Operation(
        summary = "Obtener todos los productos",
        description = "Recupera la lista completa de productos disponibles",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Lista de productos obtenida correctamente",
                content = @Content(schema = @Schema(implementation = Producto[].class)))
        })
    public List<Producto> listar() {
        return service.obtenerTodos();
    }

    @GetMapping("/{id}")
    @Operation(
        summary = "Obtener producto por ID",
        description = "Recupera los detalles de un producto específico",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Producto encontrado",
                content = @Content(schema = @Schema(implementation = Producto.class))),
            @ApiResponse(
                responseCode = "404",
                description = "Producto no encontrado",
                content = @Content)
        })
    public Producto obtener(
        @Parameter(description = "ID único del producto", example = "1", required = true)
        @PathVariable Long id) {
        return service.obtenerPorId(id);
    }

    @PutMapping("/{id}")
    @Operation(
        summary = "Actualizar producto existente",
        description = "Modifica los datos de un producto registrado",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Producto actualizado exitosamente",
                content = @Content(schema = @Schema(implementation = Producto.class))),
            @ApiResponse(
                responseCode = "404",
                description = "Producto no encontrado para actualizar",
                content = @Content),
            @ApiResponse(
                responseCode = "400",
                description = "Datos de actualización inválidos",
                content = @Content)
        })
    public ResponseEntity<Producto> actualizar(
        @Parameter(description = "ID del producto a actualizar", example = "1", required = true)
        @PathVariable Long id,
        @Parameter(description = "Datos actualizados del producto", required = true,
                  content = @Content(schema = @Schema(implementation = Producto.class)))
        @RequestBody Producto producto) {
        Producto actualizado = service.actualizar(id, producto);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    @Operation(
        summary = "Eliminar producto",
        description = "Remueve permanentemente un producto del sistema",
        responses = {
            @ApiResponse(
                responseCode = "204",
                description = "Producto eliminado exitosamente"),
            @ApiResponse(
                responseCode = "404",
                description = "Producto no encontrado para eliminar")
        })
    public void eliminar(
        @Parameter(description = "ID del producto a eliminar", example = "1", required = true)
        @PathVariable Long id) {
        service.eliminar(id);
    }
}