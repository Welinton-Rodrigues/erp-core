package com.gestaowelinton.erp.dto.fornecedor;

public record AtualizarFornecedorDto(
    String nomeRazaoSocial,
    String telefone,
    String email
) {}