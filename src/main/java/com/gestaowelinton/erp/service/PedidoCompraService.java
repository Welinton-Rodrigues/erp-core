package com.gestaowelinton.erp.service;

import com.gestaowelinton.erp.dto.compra.CriarPedidoCompraRequestDto;
import com.gestaowelinton.erp.dto.compra.ItemPedidoCompraRequestDto;
import com.gestaowelinton.erp.model.*;
import com.gestaowelinton.erp.repository.FornecedorRepository;
import com.gestaowelinton.erp.repository.PedidoCompraRepository;
import com.gestaowelinton.erp.repository.VariacaoProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.NoSuchElementException;

@Service
public class PedidoCompraService {

    @Autowired
    private PedidoCompraRepository pedidoCompraRepository;

    @Autowired
    private FornecedorRepository fornecedorRepository;

    @Autowired
    private VariacaoProdutoRepository variacaoProdutoRepository;

    @Transactional
    public PedidoCompra criarPedido(CriarPedidoCompraRequestDto dto) {
        // 1. Validar e buscar o Fornecedor.
        Fornecedor fornecedor = fornecedorRepository.findById(dto.idFornecedor())
                .orElseThrow(() -> new NoSuchElementException("Fornecedor não encontrado com o ID: " + dto.idFornecedor()));

        // 2. Preparar o Pedido de Compra "pai".
        PedidoCompra novoPedido = new PedidoCompra();
        novoPedido.setFornecedor(fornecedor);
        novoPedido.setEmpresa(fornecedor.getEmpresa()); // A empresa é a mesma do fornecedor
        novoPedido.setDataPedido(LocalDate.now());
        novoPedido.setStatus("SOLICITADO"); // O pedido começa com o status "Solicitado"
        novoPedido.setObservacoes(dto.observacoes());
        novoPedido.setItens(new ArrayList<>());

        BigDecimal valorTotalPedido = BigDecimal.ZERO;

        // 3. Processar cada item do pedido.
        for (ItemPedidoCompraRequestDto itemDto : dto.itens()) {
            VariacaoProduto variacao = variacaoProdutoRepository.findById(itemDto.idVariacaoProduto())
                    .orElseThrow(() -> new NoSuchElementException("Variação de produto não encontrada com o ID: " + itemDto.idVariacaoProduto()));

            ItemPedidoCompra novoItem = new ItemPedidoCompra();
            novoItem.setPedidoCompra(novoPedido); // Link reverso para o "pai"
            novoItem.setVariacaoProduto(variacao);
            novoItem.setQuantidade(itemDto.quantidade());
            novoItem.setPrecoCusto(itemDto.precoCusto());
            
            // Calcula o total do item
            BigDecimal valorTotalItem = itemDto.precoCusto().multiply(itemDto.quantidade());
            novoItem.setValorTotal(valorTotalItem);
            
            novoPedido.getItens().add(novoItem);
            
            // Soma o valor do item ao total do pedido
            valorTotalPedido = valorTotalPedido.add(valorTotalItem);
        }

        // 4. Atribui o valor total calculado e salva o pedido.
        novoPedido.setValorTotal(valorTotalPedido);

        // O CascadeType.ALL salvará o pedido e todos os seus itens de uma vez.
        return pedidoCompraRepository.save(novoPedido);
    }
}