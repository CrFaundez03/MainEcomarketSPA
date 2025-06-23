package com.bepyr.ecomarket.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Resena {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String comentario;
    private int calificacion;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;
}