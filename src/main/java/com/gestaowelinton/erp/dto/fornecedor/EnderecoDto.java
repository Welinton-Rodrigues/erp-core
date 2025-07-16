package com.gestaowelinton.erp.dto.fornecedor;

import com.gestaowelinton.erp.model.EnderecosCliente;

public record EnderecoDto(
    Long idEndereco,
    String logradouro,
    String numero,
    String complemento,
    String bairro,
    String cidade,
    String uf,
    String cep,
    String tipoEndereco
) {
    public EnderecoDto(EnderecosCliente endereco) {
        this(endereco.getIdEndereco(), endereco.getLogradouro(), endereco.getNumero(), endereco.getComplemento(), endereco.getBairro(), endereco.getCidade(), endereco.getUf(), endereco.getCep(), endereco.getTipoEndereco());
    }
}