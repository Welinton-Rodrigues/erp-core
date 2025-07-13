package com.gestaowelinton.erp.dto;

public record CriarFornecedorDto(
    Long idEmpresa, // Precisamos saber a qual empresa este fornecedor pertence
    String nomeRazaoSocial,
    String cnpj,
    String telefone,
    String email
) {}