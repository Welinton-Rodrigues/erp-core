package com.gestaowelinton.erp.security.dto;

// DTO para a requisição de login (o que o usuário envia)
public record AuthRequestDto(
    String email,
    String senha
) {}