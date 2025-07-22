package com.gestaowelinton.erp.dto.cliente; // Verifique o nome do seu pacote

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AtualizarClienteDto(
    @NotBlank(message = "O nome/razão social não pode ficar em branco.")
    @Size(min = 2, max = 255, message = "O nome deve ter entre 2 e 255 caracteres.")
    String nomeRazaoSocial,

    String tipoCliente,

    @Size(max = 20, message = "O telefone não pode exceder 20 caracteres.")
    String telefonePrincipal,

    @Email(message = "O formato do e-mail é inválido.")
    @Size(max = 255, message = "O e-mail não pode exceder 255 caracteres.")
    String emailPrincipal
) {}