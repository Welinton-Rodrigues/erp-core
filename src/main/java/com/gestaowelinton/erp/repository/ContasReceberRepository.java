package com.gestaowelinton.erp.repository;

import com.gestaowelinton.erp.model.ContasReceber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContasReceberRepository extends JpaRepository<ContasReceber, Long> {
    // No futuro, podemos adicionar métodos aqui para buscar contas por cliente, etc.
}