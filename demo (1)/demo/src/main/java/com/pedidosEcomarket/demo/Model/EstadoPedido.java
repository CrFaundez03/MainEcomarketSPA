package com.pedidosEcomarket.demo.Model;

public enum EstadoPedido {
    RECIBIDO,      // Estado inicial
    EN_RUTA,       // Pedido en camino
    ENTREGADO,     // Final exitoso
    RECHAZADO      // Final fallido
}