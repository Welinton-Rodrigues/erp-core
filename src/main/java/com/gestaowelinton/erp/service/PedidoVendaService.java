package com.gestaowelinton.erp.service;

import com.gestaowelinton.erp.dto.pedido.CriarPedidoVendaRequestDto;
import com.gestaowelinton.erp.dto.pedido.ItemPedidoVendaRequestDto;
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
}