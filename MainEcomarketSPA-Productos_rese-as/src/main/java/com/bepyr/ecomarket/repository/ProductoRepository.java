package com.bepyr.ecomarket.repository;

import com.bepyr.ecomarket.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {}