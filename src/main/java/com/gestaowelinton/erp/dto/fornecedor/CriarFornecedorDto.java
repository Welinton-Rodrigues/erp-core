package com.gestaowelinton.erp.dto.fornecedor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CriarFornecedorDto(
    @NotNull(message = "O idEmpresa é obrigatório.")
    Long idEmpresa,

    @NotBlank(message = "O nome/razão social é obrigatório.")
    @Size(max = 255, message = "O nome não pode exceder 255 caracteres.")
    String nomeRazaoSocial,

    @Size(max = 18, message = "O CNPJ não pode exceder 18 caracteres.")
    String cnpj,

    @Size(max = 20, message = "O telefone não pode exceder 20 caracteres.")
    String telefone,

    
    @Email(message = "O formato do e-mail é inválido.")
    @Size(max = 255, message = "O e-mail não pode exceder 255 caracteres.")
    String email
) {}