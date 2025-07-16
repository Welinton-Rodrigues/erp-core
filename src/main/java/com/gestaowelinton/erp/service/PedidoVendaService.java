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
import com.gestaowelinton.erp.repository.ContasReceberRepository;

@Service
public class PedidoVendaService {

    @Autowired
    private PedidoVendaRepository pedidoVendaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

     @Autowired
    private ContasReceberRepository contasReceberRepository;

    @Autowired
    private VariacaoProdutoRepository variacaoProdutoRepository;

   // Dentro da classe PedidoVendaService.java

    @Transactional
    public PedidoVenda criarPedido(CriarPedidoVendaRequestDto pedidoDto) {
        // 1. Validações iniciais (continuam as mesmas)
        Cliente cliente = clienteRepository.findById(pedidoDto.idCliente())
                .orElseThrow(() -> new NoSuchElementException("Cliente não encontrado com o ID: " + pedidoDto.idCliente()));

        // 2. Preparar o Pedido de Venda "pai"
        PedidoVenda novoPedido = new PedidoVenda();
        novoPedido.setCliente(cliente);
        novoPedido.setEmpresa(cliente.getEmpresa());
        novoPedido.setDataPedido(LocalDate.now());
        novoPedido.setFormaPagamento(pedidoDto.formaPagamento()); // <-- Importante
        novoPedido.setObservacoes(pedidoDto.observacoes());
        novoPedido.setItens(new ArrayList<>());

        BigDecimal valorTotalPedido = BigDecimal.ZERO;

        // 3. Processar itens e dar baixa no estoque (continua o mesmo)
        for (ItemPedidoVendaRequestDto itemDto : pedidoDto.itens()) {
            VariacaoProduto variacao = variacaoProdutoRepository.findById(itemDto.idVariacaoProduto())
                    .orElseThrow(() -> new NoSuchElementException("Variação de produto não encontrada com o ID: " + itemDto.idVariacaoProduto()));

            if (variacao.getQuantidadeEstoque() < itemDto.quantidade().intValue()) {
                throw new IllegalStateException("Estoque insuficiente para o produto: " + variacao.getProduto().getNome());
            }

            ItemPedidoVenda novoItem = new ItemPedidoVenda();
            novoItem.setPedidoVenda(novoPedido);

            novoItem.setVariacaoProduto(variacao);
            novoItem.setQuantidade(itemDto.quantidade());
            novoItem.setPrecoUnitario(variacao.getPrecoVenda());
            novoItem.setValorTotal(variacao.getPrecoVenda().multiply(itemDto.quantidade()));
            
            novoPedido.getItens().add(novoItem);

            variacao.setQuantidadeEstoque(variacao.getQuantidadeEstoque() - itemDto.quantidade().intValue());
            // O save da variação acontece no final da transação pelo Hibernate

            valorTotalPedido = valorTotalPedido.add(novoItem.getValorTotal());
        }

        novoPedido.setValorTotal(valorTotalPedido);

        // --- NOVA LÓGICA DE NEGÓCIO AQUI ---
        // 4. Decidir o que fazer com base na forma de pagamento
        boolean pagamentoImediato = "PIX".equalsIgnoreCase(pedidoDto.formaPagamento()) ||
                                    "DINHEIRO".equalsIgnoreCase(pedidoDto.formaPagamento()) ||
                                    "CARTÃO DE DÉBITO".equalsIgnoreCase(pedidoDto.formaPagamento());

        if (pagamentoImediato) {
            // Se o pagamento é na hora, já concluímos tudo
            novoPedido.setStatus("CONCLUÍDO");
            
            ContasReceber contaPaga = new ContasReceber();
            contaPaga.setEmpresa(novoPedido.getEmpresa());
            contaPaga.setCliente(novoPedido.getCliente());
            contaPaga.setPedidoVenda(novoPedido);
            contaPaga.setDescricao("Recebimento da Venda #" + novoPedido.getIdPedidoVenda()); // O ID será preenchido no save
            contaPaga.setValor(novoPedido.getValorTotal());
            contaPaga.setDataVencimento(LocalDate.now());
            contaPaga.setDataRecebimento(LocalDate.now()); // Já foi recebido!
            contaPaga.setStatus("PAGO");
            
            // Primeiro salvamos o pedido para que ele tenha um ID
            PedidoVenda pedidoSalvo = pedidoVendaRepository.save(novoPedido);
            // Associamos a conta ao pedido salvo e salvamos a conta
            contaPaga.setDescricao("Recebimento da Venda #" + pedidoSalvo.getIdPedidoVenda());
            contasReceberRepository.save(contaPaga);
            
            return pedidoSalvo;

        } else {
            // Se for a prazo (Boleto, Faturado, etc.), o pedido fica pendente de faturamento
            novoPedido.setStatus("EMITIDO");
            return pedidoVendaRepository.save(novoPedido);
        }
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
    
        }

        // 5. Salva o pedido com o novo status. O Hibernate salvará as alterações nas variações também.
        PedidoVenda pedidoCancelado = pedidoVendaRepository.save(pedido);

        // 6. Retorna o DTO do pedido atualizado.
        return new PedidoVendaResponseDto(pedidoCancelado);
    }

    /**
     * Fatura um pedido de venda, alterando seu status e gerando a conta a receber.
     * @param id O ID do pedido a ser faturado.
     * @return O DTO do pedido com o status atualizado para "FATURADO".
     */
    @Transactional
    public PedidoVendaResponseDto faturarPedido(Long id) {
        // 1. Busca o pedido no banco.
        PedidoVenda pedido = pedidoVendaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Pedido de Venda não encontrado com o ID: " + id));

        // 2. REGRA DE NEGÓCIO: Só podemos faturar um pedido que foi "EMITIDO".
        if (!"EMITIDO".equals(pedido.getStatus())) {
            throw new IllegalStateException("Apenas pedidos com status 'EMITIDO' podem ser faturados. Status atual: " + pedido.getStatus());
        }

        // 3. Altera o status do pedido.
        pedido.setStatus("FATURADO");

        // 4. REGRA DE NEGÓCIO: Cria o registro financeiro correspondente.
        ContasReceber novaConta = new ContasReceber();
        novaConta.setEmpresa(pedido.getEmpresa());
        novaConta.setCliente(pedido.getCliente());
        novaConta.setPedidoVenda(pedido);
        novaConta.setDescricao("Recebimento referente à Venda #" + pedido.getIdPedidoVenda());
        novaConta.setValor(pedido.getValorTotal());
        novaConta.setDataVencimento(LocalDate.now().plusDays(1)); // Simula o D+1 do recebimento da maquininha
        novaConta.setStatus("A RECEBER");
        
        // 5. Salva a nova conta a receber no banco de dados.
        contasReceberRepository.save(novaConta);

        // 6. Salva o pedido com o novo status.
        PedidoVenda pedidoFaturado = pedidoVendaRepository.save(pedido);

        return new PedidoVendaResponseDto(pedidoFaturado);
    }


    
}