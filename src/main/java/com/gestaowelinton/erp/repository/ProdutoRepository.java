package com.gestaowelinton.erp.repository;

import com.gestaowelinton.erp.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    List<Produto> findByEmpresaIdEmpresa(Long idEmpresa);

}