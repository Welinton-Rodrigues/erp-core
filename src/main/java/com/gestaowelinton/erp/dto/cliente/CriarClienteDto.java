package com.gestaowelinton.erp.dto.cliente;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CriarClienteDto(
    @NotNull(message = "O idEmpresa não pode ser nulo.")
    Long idEmpresa,

    @NotBlank(message = "O nome/razão social é obrigatório.")
    @Size(min = 2, max = 255, message = "O nome deve ter entre 2 e 255 caracteres.")
    String nomeRazaoSocial,

    @NotBlank(message = "O CPF/CNPJ é obrigatório.")
    @Size(min = 11, max = 18, message = "O CPF/CNPJ deve ter entre 11 e 18 caracteres.")
    String cpfCnpj,

    @NotBlank(message = "O tipo de cliente é obrigatório.")
    String tipoCliente,
    String telefonePrincipal,
    String emailPrincipal
) {}