package com.pedidosEcomarket.demo.Model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pedido")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Pedido {
   public enum Estado {
        EN_PREPARACION("En preparación"),
        EN_CAMINO("En camino"),
        ENTREGADO("Entregado"),
        CANCELADO("Cancelado");

        private final String descripcion;

        Estado(String descripcion) {
            this.descripcion = descripcion;
        }

        public String getDescripcion() {
            return descripcion;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String codigoPedido;

    @Column(nullable = false)
    private String cliente;

    @Column(nullable = false)
    private String fechaPedido;

    @Column(nullable = false)
    private String fechaEntrega;

    @Column(nullable = false)
    private String direccionEntrega;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Estado estado;

    @Column(nullable = false)
    private Double total;
} 

