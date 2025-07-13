package com.gestaowelinton.erp.repository;

import com.gestaowelinton.erp.model.VariacaoProduto;
import org.springframework.data.jpa.repository.JpaRepository; // <-- Importante
import org.springframework.stereotype.Repository;

@Repository
// A PARTE MAIS IMPORTANTE É ESTA: extends JpaRepository<VariacaoProduto, Long>
public interface VariacaoProdutoRepository extends JpaRepository<VariacaoProduto, Long> {
}