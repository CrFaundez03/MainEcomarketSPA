package com.pedidosEcomarket.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.pedidosEcomarket.demo.Model.Pedido;
import jakarta.transaction.Transactional;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    @Transactional
    @Modifying
    @Query("UPDATE Pedido p SET p.estado = :estado WHERE p.id = :id")
    void actualizarEstado(@Param("id") Integer id, @Param("estado") Pedido.Estado estado);
}
