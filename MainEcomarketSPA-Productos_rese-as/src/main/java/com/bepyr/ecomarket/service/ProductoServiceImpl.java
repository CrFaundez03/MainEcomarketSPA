package com.bepyr.ecomarket.service;

import com.bepyr.ecomarket.model.Producto;
import com.bepyr.ecomarket.repository.ProductoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Tag(name = "Servicio de Productos", description = "Contiene todas las operaciones CRUD para la gestión de productos")
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository repo;

    @Override
    @Operation(
        summary = "Crear producto",
        description = "Registra un nuevo producto en la base de datos",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Producto creado exitosamente",
                content = @Content(schema = @Schema(implementation = Producto.class))),
            @ApiResponse(
                responseCode = "400",
                description = "Datos del producto inválidos")
        })
    public Producto guardarProducto(
        @Parameter(description = "Objeto Producto a crear", required = true)
        Producto producto) {
        return repo.save(producto);
    }

    @Override
    @Operation(
        summary = "Obtener todos los productos",
        description = "Recupera una lista completa de todos los productos disponibles",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Lista de productos obtenida correctamente",
                content = @Content(schema = @Schema(implementation = Producto[].class)))
        })
    public List<Producto> obtenerTodos() {
        return repo.findAll();
    }

    @Override
    @Operation(
        summary = "Buscar producto por ID",
        description = "Obtiene un producto específico según su identificador único",
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
    public Producto obtenerPorId(
        @Parameter(description = "ID único del producto", example = "1", required = true)
        Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    @Operation(
        summary = "Actualizar producto",
        description = "Modifica los datos de un producto existente",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Producto actualizado exitosamente",
                content = @Content(schema = @Schema(implementation = Producto.class))),
            @ApiResponse(
                responseCode = "404",
                description = "Producto no encontrado para actualizar"),
            @ApiResponse(
                responseCode = "400",
                description = "Datos de actualización inválidos")
        })
    public Producto actualizar(
        @Parameter(description = "ID del producto a actualizar", example = "1", required = true)
        Long id,
        @Parameter(description = "Datos actualizados del producto", required = true)
        Producto productoActualizado) {
        
        Producto existente = repo.findById(id)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        existente.setNombre(productoActualizado.getNombre());
        existente.setDescripcion(productoActualizado.getDescripcion());
        existente.setPrecio(productoActualizado.getPrecio());

        return repo.save(existente);
    }

    @Override
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
        Long id) {
        repo.deleteById(id);
    }
}