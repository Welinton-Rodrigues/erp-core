package com.gestaowelinton.erp.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn; // <- Importante adicionar
import jakarta.persistence.ManyToOne; // <- Importante adicionar
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "clientes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer idCliente;

  // --- NOVO RELACIONAMENTO ADICIONADO AQUI ---
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_empresa", nullable = false)
  private Empresa empresa;
  // -----------------------------------------

  @Column(name = "nome_razao_social", nullable = false, length = 255)
  private String nomeRazaoSocial;

  @Column(name = "cpf_cnpj", nullable = false, unique = true, length = 18)
  private String cpfCnpj;

  @Column(name = "tipo_cliente", nullable = false, length = 50)
  private String tipoCliente;

  @Column(name = "data_cadastro")
  private LocalDate dataCadastro = LocalDate.now();

  @Column(name = "telefone_principal", length = 20)
  private String telefonePrincipal;

  @Column(name = "email_principal", length = 255)
  private String emailPrincipal;
// ... (outros campos do Cliente) ...

@OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
private List<EnderecosCliente> enderecos = new ArrayList<>(); // <-- CORREÇÃO AQUI

@OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
private List<ContatosCliente> contatos = new ArrayList<>(); // <-- CORREÇÃO AQUI

@OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
private List<PedidoVenda> pedidosVenda = new ArrayList<>(); // <-- CORREÇÃO AQUI

@OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
private List<ContasReceber> contasReceber = new ArrayList<>(); // <-- CORREÇÃO AQUI

}