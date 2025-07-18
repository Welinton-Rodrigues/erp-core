package com.gestaowelinton.erp.security.repository;

import com.gestaowelinton.erp.security.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // O Spring Data JPA criará a query automaticamente para buscar um usuário pelo email.
    Optional<Usuario> findByEmail(String email);

}