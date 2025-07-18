package com.gestaowelinton.erp.security.dto;

public record RegisterRequestDto(
    String nome,
    String email,
    String senha,
    String cargo, // Ex: "ROLE_ADMIN" ou "ROLE_VENDEDOR"
    Long idEmpresa // A qual empresa este novo usuário pertence
) {}