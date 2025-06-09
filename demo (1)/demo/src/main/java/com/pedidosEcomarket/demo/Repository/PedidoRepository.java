package com.pedidosEcomarket.demo.Repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.pedidosEcomarket.demo.Model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    Optional<Pedido> findByCodigoSeguimiento(String codigoSeguimiento);
    boolean existsByCodigoSeguimiento(String codigoSeguimiento);
}
