package com.gestaowelinton.erp.controller;

import com.gestaowelinton.erp.dto.compra.CriarPedidoCompraRequestDto;
import com.gestaowelinton.erp.dto.compra.PedidoCompraResponseDto;
import com.gestaowelinton.erp.model.PedidoCompra;
import com.gestaowelinton.erp.service.PedidoCompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/pedidos-compra")
public class PedidoCompraController {

    @Autowired
    private PedidoCompraService pedidoCompraService;

    @PostMapping
    public ResponseEntity<?> criarPedidoCompra(@Valid @RequestBody CriarPedidoCompraRequestDto dto) {
        try {
            PedidoCompra novoPedido = pedidoCompraService.criarPedido(dto);
            
            return new ResponseEntity<>(novoPedido, HttpStatus.CREATED);
        } catch (NoSuchElementException e) {
            
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Ocorreu um erro inesperado ao criar o pedido de compra.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
@PutMapping("/{id}/receber")
    public ResponseEntity<?> receberPedido(@PathVariable Long id) {
        try {
            PedidoCompraResponseDto pedidoRecebido = pedidoCompraService.receberPedido(id);
            return new ResponseEntity<>(pedidoRecebido, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


}