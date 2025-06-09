package com.pedidosEcomarket.demo.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pedidosEcomarket.demo.Model.EstadoPedido;
import com.pedidosEcomarket.demo.Model.Pedido;
import com.pedidosEcomarket.demo.Repository.PedidoRepository;

@Service
@Transactional
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public Pedido crearPedido(Pedido pedido) {
        if (pedidoRepository.existsByCodigoSeguimiento(pedido.getCodigoSeguimiento())) {
            throw new RuntimeException("El código de seguimiento ya existe");
        }
        return pedidoRepository.save(pedido);
    }

    public Pedido actualizarEstado(Long id, EstadoPedido nuevoEstado) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        // Validar transiciones válidas
        if (pedido.getEstado() == EstadoPedido.RECIBIDO && nuevoEstado == EstadoPedido.EN_RUTA) {
            pedido.setEstado(nuevoEstado);
        } else if (pedido.getEstado() == EstadoPedido.EN_RUTA && 
                  (nuevoEstado == EstadoPedido.ENTREGADO || nuevoEstado == EstadoPedido.RECHAZADO)) {
            pedido.setEstado(nuevoEstado);
        } else {
            throw new IllegalStateException("Transición de estado no permitida");
        }

        return pedidoRepository.save(pedido);
    }
    public Pedido buscarPorCodigo(String codigoSeguimiento){
        return pedidoRepository.findByCodigoSeguimiento(codigoSeguimiento)
            .orElseThrow(() -> new PedidoNotFoundException(
                "No se encontró ningún pedido con el código de seguimiento: " + codigoSeguimiento
            ));
}
    }
    





