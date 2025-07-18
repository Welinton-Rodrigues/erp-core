package com.gestaowelinton.erp.security.dto;

// DTO para a resposta de login (o que o servidor devolve)
public record AuthResponseDto(
    String token
) {}