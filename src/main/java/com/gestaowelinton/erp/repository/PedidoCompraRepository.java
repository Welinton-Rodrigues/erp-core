package com.gestaowelinton.erp.repository;

import com.gestaowelinton.erp.model.PedidoCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoCompraRepository extends JpaRepository<PedidoCompra, Long> {
    // Futuramente, podemos adicionar métodos de busca aqui
}