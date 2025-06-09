package com.pedidosEcomarket.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.pedidosEcomarket.demo.Model.EstadoPedido;
import com.pedidosEcomarket.demo.Model.Pedido;
import com.pedidosEcomarket.demo.Service.PedidoService;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    // Crear pedido (estado inicial: RECIBIDO)
    @PostMapping
    public Pedido crearPedido(@RequestBody Pedido pedido) {
        pedido.setEstado(EstadoPedido.RECIBIDO);
        return pedidoService.crearPedido(pedido);
    }

    // Actualizar estado (transaccional)
    @PatchMapping("/{id}/estado")
    public Pedido actualizarEstado(
            @PathVariable Long id,
            @RequestParam EstadoPedido nuevoEstado) {
        return pedidoService.actualizarEstado(id, nuevoEstado);
    }

    // Consultar por código de seguimiento
    @GetMapping("/{codigoSeguimiento}")
    public Pedido buscarPorCodigo(@PathVariable String codigoSeguimiento) {
        return pedidoService.buscarPorCodigo(codigoSeguimiento);
    }
}



