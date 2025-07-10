package com.gestaowelinton.erp.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "enderecos_cliente")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnderecosCliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_endereco")
    private Long idEndereco;

    // Relacionamento principal: Este endereço pertence a UM cliente.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    @Column(name = "logradouro", nullable = false, length = 255)
    private String logradouro; // Rua, Avenida, etc.

    @Column(name = "numero", length = 20)
    private String numero;

    @Column(name = "complemento", length = 100)
    private String complemento; // Apto, Bloco, Casa, etc.

    @Column(name = "bairro", nullable = false, length = 100)
    private String bairro;

    @Column(name = "cidade", nullable = false, length = 100)
    private String cidade;

    @Column(name = "uf", nullable = false, length = 2) // Sigla do estado, ex: "SP"
    private String uf;

    @Column(name = "cep", nullable = false, length = 9) // Formato "12345-678"
    private String cep;

    @Column(name = "tipo_endereco", length = 50)
    private String tipoEndereco; // Ex: "Residencial", "Comercial", "Entrega"
}