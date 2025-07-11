package com.gestaowelinton.erp.repository;

import com.gestaowelinton.erp.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    // Método para listar todos os produtos de uma empresa específica.
    // O Spring Data JPA criará a query automaticamente.
    List<Produto> findByEmpresaIdEmpresa(Long idEmpresa);

}