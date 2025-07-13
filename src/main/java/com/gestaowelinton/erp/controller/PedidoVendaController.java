package com.gestaowelinton.erp.controller;

import com.gestaowelinton.erp.dto.pedido.CriarPedidoVendaRequestDto;
import com.gestaowelinton.erp.model.PedidoVenda;
import com.gestaowelinton.erp.service.PedidoVendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/pedidos-venda") // <-- Garanta que está no plural
public class PedidoVendaController {

    @Autowired
    private PedidoVendaService pedidoVendaService;

    @PostMapping
    public ResponseEntity<?> criarPedido(@RequestBody CriarPedidoVendaRequestDto pedidoDto) {
        try {
            PedidoVenda novoPedido = pedidoVendaService.criarPedido(pedidoDto);
            // No futuro, criaremos um DTO de resposta para o pedido também.
            // Por enquanto, retornar a entidade ajuda no debug.
            return new ResponseEntity<>(novoPedido, HttpStatus.CREATED);
        } catch (NoSuchElementException | IllegalStateException e) {
            // Captura erros de "item não encontrado" ou "sem estoque"
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // Captura qualquer outro erro inesperado
            return new ResponseEntity<>("Ocorreu um erro inesperado ao processar o pedido.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}