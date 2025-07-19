package com.gestaowelinton.erp.repository;

import com.gestaowelinton.erp.model.ContasPagar; // <-- Corrigido
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContasPagarRepository extends JpaRepository<ContasPagar, Long> { // <-- Corrigido
}