package com.gestaowelinton.erp.service;

import com.gestaowelinton.erp.dto.financeiro.ContasReceberResponseDto;
import com.gestaowelinton.erp.model.ContasReceber;
import com.gestaowelinton.erp.repository.ContasReceberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.NoSuchElementException;

@Service
public class ContasReceberService {

    @Autowired
    private ContasReceberRepository contasReceberRepository;

    /**
     * Quita uma conta a receber, mudando seu status para "PAGO"
     * e registrando a data de recebimento.
     * @param idConta O ID da conta a receber a ser quitada.
     * @return O DTO da conta atualizada.
     */
    @Transactional
    public ContasReceberResponseDto quitarConta(Long idConta) {
        ContasReceber conta = contasReceberRepository.findById(idConta)
                .orElseThrow(() -> new NoSuchElementException("Conta a Receber não encontrada com o ID: " + idConta));

        if (!"A RECEBER".equals(conta.getStatus())) {
            throw new IllegalStateException("Apenas contas com status 'A RECEBER' podem ser quitadas.");
        }

        conta.setStatus("PAGO");
        conta.setDataRecebimento(LocalDate.now());

        ContasReceber contaPaga = contasReceberRepository.save(conta);
        return new ContasReceberResponseDto(contaPaga);
    }
    
}