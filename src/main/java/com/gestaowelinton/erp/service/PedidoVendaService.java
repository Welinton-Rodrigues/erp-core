package com.gestaowelinton.erp.service;

import com.gestaowelinton.erp.dto.pedido.CriarPedidoVendaRequestDto;
import com.gestaowelinton.erp.dto.pedido.ItemPedidoVendaRequestDto;
import com.gestaowelinton.erp.dto.pedido.PedidoVendaResponseDto;
import com.gestaowelinton.erp.model.*;
import com.gestaowelinton.erp.repository.ClienteRepository;
import com.gestaowelinton.erp.repository.PedidoVendaRepository;
import com.gestaowelinton.erp.repository.VariacaoProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class PedidoVendaService {

    @Autowired
    private PedidoVendaRepository pedidoVendaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private VariacaoProdutoRepository variacaoProdutoRepository;

    @Transactional // ESSENCIAL: Garante que ou tudo funciona, ou nada é salvo.
    public PedidoVenda criarPedido(CriarPedidoVendaRequestDto pedidoDto) {
        // 1. Validar e buscar o Cliente
        Cliente cliente = clienteRepository.findById(pedidoDto.idCliente())
                .orElseThrow(() -> new NoSuchElementException("Cliente não encontrado com o ID: " + pedidoDto.idCliente()));

        // 2. Preparar o Pedido de Venda "pai"
        PedidoVenda novoPedido = new PedidoVenda();
        novoPedido.setCliente(cliente);
        novoPedido.setEmpresa(cliente.getEmpresa()); // Pega a empresa do cliente
        novoPedido.setDataPedido(LocalDate.now());
        novoPedido.setStatus("EMITIDO");
        novoPedido.setObservacoes(pedidoDto.observacoes());
        novoPedido.setItens(new ArrayList<>());

        BigDecimal valorTotalPedido = BigDecimal.ZERO;

        // 3. Processar cada item do pedido
        for (ItemPedidoVendaRequestDto itemDto : pedidoDto.itens()) {
            // 3.1. Buscar a variação do produto no banco
            VariacaoProduto variacao = variacaoProdutoRepository.findById(itemDto.idVariacaoProduto())
                    .orElseThrow(() -> new NoSuchElementException("Variação de produto não encontrada com o ID: " + itemDto.idVariacaoProduto()));

            // 3.2. REGRA DE NEGÓCIO: Verificar estoque
            if (variacao.getQuantidadeEstoque() < itemDto.quantidade().intValue()) {
                throw new IllegalStateException("Estoque insuficiente para o produto: " + variacao.getProduto().getNome() + " (Cor: " + variacao.getCor() + ", Tamanho: " + variacao.getTamanho() + ")");
            }

            // 3.3. Criar a entidade ItemPedidoVenda
            ItemPedidoVenda novoItem = new ItemPedidoVenda();
            novoItem.setPedidoVenda(novoPedido); // Linka o item com o pedido pai
            novoItem.setVariacaoProduto(variacao);
            novoItem.setQuantidade(itemDto.quantidade());
            novoItem.setPrecoUnitario(variacao.getPrecoVenda()); // Pega o preço do banco, não do front-end!
            novoItem.setValorTotal(variacao.getPrecoVenda().multiply(itemDto.quantidade()));

            // 3.4. Adicionar o item à lista do pedido
            novoPedido.getItens().add(novoItem);

            // 3.5. REGRA DE NEGÓCIO: Dar baixa no estoque
            variacao.setQuantidadeEstoque(variacao.getQuantidadeEstoque() - itemDto.quantidade().intValue());
            variacaoProdutoRepository.save(variacao); // Salva a alteração do estoque

            // 3.6. Atualizar o valor total do pedido
            valorTotalPedido = valorTotalPedido.add(novoItem.getValorTotal());
        }

        // 4. Finalizar e salvar o pedido
        novoPedido.setValorTotal(valorTotalPedido);
        
        return pedidoVendaRepository.save(novoPedido);
    }

    /**
     * Busca um pedido de venda pelo seu ID e o converte para DTO.
     * @param id O ID do pedido.
     * @return Um DTO com os detalhes do pedido.
     * @throws NoSuchElementException se o pedido não for encontrado.
     */
    @Transactional(readOnly = true)
    public PedidoVendaResponseDto buscarPedidoPorId(Long id) {
        // 1. Busca a entidade no banco
        PedidoVenda pedido = pedidoVendaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Pedido de Venda não encontrado com o ID: " + id));
        
        // 2. Converte a entidade para o DTO de resposta e retorna
        return new PedidoVendaResponseDto(pedido);
    }

    /**
     * Lista todos os pedidos de uma empresa e os converte para DTO.
     * @param idEmpresa O ID da empresa.
     * @return Uma lista de DTOs com o resumo dos pedidos.
     */
    @Transactional(readOnly = true)
    public List<PedidoVendaResponseDto> listarPedidosPorEmpresa(Long idEmpresa) {
        // 1. Busca a lista de entidades no banco usando o novo método do repositório
        List<PedidoVenda> pedidos = pedidoVendaRepository.findByEmpresaIdEmpresa(idEmpresa);

        // 2. Converte cada entidade da lista para seu DTO correspondente
        return pedidos.stream()
                      .map(PedidoVendaResponseDto::new)
                      .collect(Collectors.toList());
    }

    // Dentro da classe PedidoVendaService.java

// ... outros métodos ...

    /**
     * Cancela um pedido de venda, alterando seu status e estornando o estoque dos itens.
     * @param id O ID do pedido a ser cancelado.
     * @return O DTO do pedido com o status atualizado para "CANCELADO".
     * @throws NoSuchElementException se o pedido não for encontrado.
     * @throws IllegalStateException se o pedido não puder ser cancelado (ex: já foi cancelado ou faturado).
     */
    @Transactional
    public PedidoVendaResponseDto cancelarPedido(Long id) {
        // 1. Busca o pedido no banco, incluindo seus itens e variações.
        PedidoVenda pedido = pedidoVendaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Pedido de Venda não encontrado com o ID: " + id));

        // 2. REGRA DE NEGÓCIO: Só podemos cancelar um pedido que foi "EMITIDO".
        if (!"EMITIDO".equals(pedido.getStatus())) {
            throw new IllegalStateException("Apenas pedidos com status 'EMITIDO' podem ser cancelados. Status atual: " + pedido.getStatus());
        }

        // 3. Altera o status do pedido.
        pedido.setStatus("CANCELADO");

        // 4. REGRA DE NEGÓCIO: Estorna (devolve) os itens para o estoque.
        for (ItemPedidoVenda item : pedido.getItens()) {
            VariacaoProduto variacao = item.getVariacaoProduto();
            int quantidadeEstornada = item.getQuantidade().intValue();
            
            // Adiciona a quantidade de volta ao estoque da variação
            variacao.setQuantidadeEstoque(variacao.getQuantidadeEstoque() + quantidadeEstornada);
            // O repositório não precisa ser chamado aqui para a variação, pois o Hibernate gerencia
            // o estado do objeto dentro de uma transação @Transactional.
        }

        // 5. Salva o pedido com o novo status. O Hibernate salvará as alterações nas variações também.
        PedidoVenda pedidoCancelado = pedidoVendaRepository.save(pedido);

        // 6. Retorna o DTO do pedido atualizado.
        return new PedidoVendaResponseDto(pedidoCancelado);
    }
}