package com.pedidosEcomarket.demo.Model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pedidos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {
    
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo_seguimiento", nullable = false, unique = true)
    private String codigoSeguimiento; // Código único para tracking

    @Column(name = "estado", nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadoPedido estado; // RECIBIDO, EN_RUTA, ENTREGADO, RECHAZADO

    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    // Campos adicionales 
    @Column(name = "cliente_id")
    private String clienteId;
}


