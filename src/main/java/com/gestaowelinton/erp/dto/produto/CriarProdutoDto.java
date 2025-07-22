package com.gestaowelinton.erp.dto.produto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

// Este é o "formulário" completo para criar um produto e suas variações
public record CriarProdutoDto(
    // Dados do Produto "Pai"
    @NotNull(message = "O idEmpresa não pode ser nulo.")
    Long idEmpresa,

    @NotBlank(message = "O nome do produto é obrigatório.")
    String nome,

    @NotBlank(message = "A descrição do produto é obrigatória.")
    String codigoInterno,

    @NotBlank(message = "A unidade de medida é obrigatórioa")
    String unidadeMedida,

    @NotBlank(message = "O status do produto é obrigatório.")
    String status,


    // Lista com as variações
    @NotEmpty(message = "A lista de variações não pode estar vazia.")
    @Valid
    List<VariacaoProdutoDto> variacoes
) {}