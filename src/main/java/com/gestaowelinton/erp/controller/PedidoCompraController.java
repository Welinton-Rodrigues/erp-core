package com.gestaowelinton.erp.controller;

import com.gestaowelinton.erp.dto.compra.CriarPedidoCompraRequestDto;
import com.gestaowelinton.erp.model.PedidoCompra;
import com.gestaowelinton.erp.service.PedidoCompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/pedidos-compra")
public class PedidoCompraController {

    @Autowired
    private PedidoCompraService pedidoCompraService;

    @PostMapping
    public ResponseEntity<?> criarPedidoCompra(@RequestBody CriarPedidoCompraRequestDto dto) {
        try {
            PedidoCompra novoPedido = pedidoCompraService.criarPedido(dto);
            // No futuro, criaremos um DTO de resposta para o Pedido de Compra.
            // Por enquanto, retornar a entidade ajuda no debug.
            return new ResponseEntity<>(novoPedido, HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            // Captura erros se o fornecedor ou uma variação de produto não forem encontrados.
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // Captura qualquer outro erro inesperado.
            return new ResponseEntity<>("Ocorreu um erro inesperado ao criar o pedido de compra.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Futuramente, adicionaremos os endpoints GET, PUT, etc.
}