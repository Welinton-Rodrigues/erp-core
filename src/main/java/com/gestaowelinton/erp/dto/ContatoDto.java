package com.gestaowelinton.erp.dto;

import com.gestaowelinton.erp.model.ContatosCliente;

public record ContatoDto(
    Long idContato,
    String nome,
    String cargo,
    String email,
    String telefone
) {
    public ContatoDto(ContatosCliente contato) {
        this(contato.getIdContato(), contato.getNome(), contato.getCargo(), contato.getEmail(), contato.getTelefone());
    }
}