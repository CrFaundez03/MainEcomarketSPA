package com.pedidosEcomarket.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pedidosEcomarket.demo.Model.Pedido;
import com.pedidosEcomarket.demo.Service.PedidoService;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public ResponseEntity<List<Pedido>> listarTodosLosPedidos() {
        List<Pedido> pedidos = pedidoService.listarTodos();
        if (pedidos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pedidos);
    }

    @PostMapping
    public ResponseEntity<Pedido> crearPedido(@RequestBody Pedido nuevoPedido) {
        Pedido pedidoCreado = pedidoService.crearPedido(nuevoPedido);
        return ResponseEntity.ok(pedidoCreado);
    }

    @PatchMapping("/{id}/avanzar-estado")
    public ResponseEntity<Void> avanzarEstado(@PathVariable Integer id) {
        pedidoService.avanzarEstado(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<Void> actualizarEstado(
            @PathVariable Integer id,
            @RequestParam String estado) {
        try {
            Pedido.Estado nuevoEstado = Pedido.Estado.valueOf(estado.toUpperCase());
            pedidoService.actualizarEstado(id, nuevoEstado);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPedido(@PathVariable Integer id) {
    pedidoService.eliminarPedido(id);
    return ResponseEntity.noContent().build();
}
}
