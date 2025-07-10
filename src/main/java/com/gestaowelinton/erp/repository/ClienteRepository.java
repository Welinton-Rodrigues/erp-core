package com.gestaowelinton.erp.repository;

import com.gestaowelinton.erp.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    // O Spring Data JPA cria a query automaticamente a partir do nome do método
    // "Encontre um Cliente pelo seu campo cpfCnpj"
    Optional<Cliente> findByCpfCnpj(String cpfCnpj);

    // "Encontre todos os Clientes que pertencem a uma Empresa, buscando pelo ID da empresa"
    List<Cliente> findByEmpresaIdEmpresa(Long idEmpresa);
}