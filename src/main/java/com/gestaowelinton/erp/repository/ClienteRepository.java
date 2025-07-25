package com.gestaowelinton.erp.repository;

import com.gestaowelinton.erp.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    Optional<Cliente> findByCpfCnpj(String cpfCnpj);

    List<Cliente> findByEmpresaIdEmpresa(Long idEmpresa);
}