package com.gestaowelinton.erp.controller;

import java.util.List;
import com.gestaowelinton.erp.dto.pedido.CriarPedidoVendaRequestDto;
import com.gestaowelinton.erp.dto.pedido.PedidoVendaResponseDto;
import com.gestaowelinton.erp.model.PedidoVenda;
import com.gestaowelinton.erp.service.PedidoVendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/pedidos-venda") 
public class PedidoVendaController {

    @Autowired
    private PedidoVendaService pedidoVendaService;

    @PostMapping
    public ResponseEntity<?> criarPedido(@Valid @RequestBody CriarPedidoVendaRequestDto pedidoDto) {
    
            PedidoVenda novoPedido = pedidoVendaService.criarPedido(pedidoDto);
           
            return new ResponseEntity<>(novoPedido, HttpStatus.CREATED);
       
    }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoVendaResponseDto> buscarPedidoPorId(@PathVariable Long id) {
     
            PedidoVendaResponseDto pedidoDto = pedidoVendaService.buscarPedidoPorId(id);
            return new ResponseEntity<>(pedidoDto, HttpStatus.OK);
        
    }

    // --- Endpoint para LISTAR todos os pedidos de uma empresa ---
    @GetMapping
    public ResponseEntity<List<PedidoVendaResponseDto>> listarPedidosPorEmpresa(@RequestParam Long idEmpresa) {
        List<PedidoVendaResponseDto> pedidos = pedidoVendaService.listarPedidosPorEmpresa(idEmpresa);
        return new ResponseEntity<>(pedidos, HttpStatus.OK);
    }


    // --- Endpoint para CANCELAR um pedido ---
    @PutMapping("/{id}/cancelar")
    public ResponseEntity<?> cancelarPedido(@PathVariable Long id) {
      
            PedidoVendaResponseDto pedidoCancelado = pedidoVendaService.cancelarPedido(id);
            return new ResponseEntity<>(pedidoCancelado, HttpStatus.OK);
       
    }

     // --- Endpoint para FATURAR um pedido ---
    @PutMapping("/{id}/faturar")
    public ResponseEntity<?> faturarPedido(@PathVariable Long id) {
   
            PedidoVendaResponseDto pedidoFaturado = pedidoVendaService.faturarPedido(id);
            return new ResponseEntity<>(pedidoFaturado, HttpStatus.OK);
     
    }
}