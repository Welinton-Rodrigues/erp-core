package com.gestaowelinton.erp.repository;

import com.gestaowelinton.erp.model.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {

    List<Fornecedor> findByEmpresaIdEmpresa(Long idEmpresa);

}