package com.gestaowelinton.erp.dto.cliente;

import java.math.BigDecimal; // Embora não usemos BigDecimal aqui, é bom ter como referência

// Este record define os campos que a API pode receber para uma atualização.
public record AtualizarClienteDto(
    String nomeRazaoSocial,
    String tipoCliente,
    String telefonePrincipal,
    String emailPrincipal
) {}