package com.gestaowelinton.erp.dto.pedido;

import java.util.List;
import com.gestaowelinton.erp.dto.pedido.ItemPedidoVendaRequestDto;
import jakarta.validation.constraints.NotBlank; 
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.Valid;

public record CriarPedidoVendaRequestDto(
    @NotNull(message = "O ID do cliente não pode ser nulo")
    Integer idCliente, 
  
    @NotBlank(message =" A forma de pagamento é obrigatória.")
    String formaPagamento,

    @NotEmpty(message = "A lista de itens não pode estar vazia.")
    @Valid
    String observacoes,
    List<ItemPedidoVendaRequestDto> itens 
) {}