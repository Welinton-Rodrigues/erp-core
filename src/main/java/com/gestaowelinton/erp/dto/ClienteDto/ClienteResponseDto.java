package com.gestaowelinton.erp.dto.ClienteDto;

import com.gestaowelinton.erp.dto.FornecedorDto.EnderecoDto;
import com.gestaowelinton.erp.model.Cliente;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public record ClienteResponseDto(
    Integer idCliente,
    String nomeRazaoSocial,
    String cpfCnpj,
    String tipoCliente,
    LocalDate dataCadastro,
    String telefonePrincipal,
    String emailPrincipal,
    List<EnderecoDto> enderecos, // <-- Usando a lista de DTOs filhos
    List<ContatoDto> contatos     // <-- Usando a lista de DTOs filhos
) {
    public ClienteResponseDto(Cliente cliente) {
        this(
            cliente.getIdCliente(),
            cliente.getNomeRazaoSocial(),
            cliente.getCpfCnpj(),
            cliente.getTipoCliente(),
            cliente.getDataCadastro(),
            cliente.getTelefonePrincipal(),
            cliente.getEmailPrincipal(),
            // Converte a lista de entidades de endereço para uma lista de DTOs de endereço
            cliente.getEnderecos().stream().map(EnderecoDto::new).collect(Collectors.toList()),
            // Converte a lista de entidades de contato para uma lista de DTOs de contato
            cliente.getContatos().stream().map(ContatoDto::new).collect(Collectors.toList())
        );
    }
}