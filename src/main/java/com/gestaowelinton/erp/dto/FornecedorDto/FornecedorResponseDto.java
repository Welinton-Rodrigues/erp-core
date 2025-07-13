package com.gestaowelinton.erp.dto.FornecedorDto;

import com.gestaowelinton.erp.model.Fornecedor;

public record FornecedorResponseDto(
    Long idFornecedor,
    String nomeRazaoSocial,
    String cnpj,
    String telefone,
    String email
) {
    public FornecedorResponseDto(Fornecedor fornecedor) {
        this(
            fornecedor.getIdFornecedor(),
            fornecedor.getNomeRazaoSocial(),
            fornecedor.getCnpj(),
            fornecedor.getTelefone(),
            fornecedor.getEmail()
        );
    }
}