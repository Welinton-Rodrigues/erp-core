package com.gestaowelinton.erp.repository;

import com.gestaowelinton.erp.model.PedidoVenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoVendaRepository extends JpaRepository<PedidoVenda, Long> {
}