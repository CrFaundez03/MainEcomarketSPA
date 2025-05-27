package com.pedidosEcomarket.demo.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pedidosEcomarket.demo.Model.Pedido;
import com.pedidosEcomarket.demo.Repository.PedidoRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    public Pedido crearPedido(Pedido nuevoPedido) {
        return pedidoRepository.save(nuevoPedido);
    }

    public void avanzarEstado(Integer id) {
        Pedido pedido = pedidoRepository.findById(id.longValue())
            .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        switch (pedido.getEstado()) {
            case EN_PREPARACION:
                pedido.setEstado(Pedido.Estado.EN_CAMINO);
                break;
            case EN_CAMINO:
                pedido.setEstado(Pedido.Estado.ENTREGADO);
                break;
            case ENTREGADO:
                throw new IllegalStateException("El pedido ya fue entregado");
            case CANCELADO:
                throw new IllegalStateException("El pedido está cancelado");
        }

        pedidoRepository.save(pedido);
    }

    public void actualizarEstado(Integer id, Pedido.Estado nuevoEstado) {
        Pedido pedido = pedidoRepository.findById(id.longValue())
            .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        pedido.setEstado(nuevoEstado);
        pedidoRepository.save(pedido);
    }

    public void eliminarPedido(Integer id) {
    Pedido pedido = pedidoRepository.findById(id.longValue())
        .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
    pedidoRepository.delete(pedido);
}
}
