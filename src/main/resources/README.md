# ERP Core System - [Nome do Seu Projeto]

![Java](https://img.shields.io/badge/Java-17-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen)
![JPA/Hibernate](https://img.shields.io/badge/JPA-Hibernate-orange)
![Maven](https://img.shields.io/badge/Build-Maven-red)

## 📄 Descrição

Este projeto é o back-end de um sistema de ERP (Enterprise Resource Planning) modular e robusto, construído com Java e Spring Boot. A arquitetura foi projetada para ser multi-inquilino (multi-empresa) e flexível, permitindo a gestão de cadastros essenciais, controle de estoque com variações de produto e um fluxo de vendas transacional.

O objetivo é fornecer uma base sólida que pode ser estendida e customizada para atender às necessidades de diferentes tipos de pequenos e médios negócios, como lojas de varejo (vestuário, etc.), prestadores de serviço e mais.

## ✨ Funcionalidades Principais

* **Arquitetura Multi-Inquilino:** O sistema é projetado para que múltiplos clientes (empresas) possam usar a mesma instância da aplicação com seus dados totalmente isolados.
* **Gestão de Cadastros (CRUD):**
    * [✅] Módulo de Clientes
    * [✅] Módulo de Produtos
    * [✅] Módulo de Fornecedores
* **Controle de Estoque Detalhado:**
    * Suporte para produtos com variações (SKUs), como Cor e Tamanho.
    * Controle de estoque individual para cada variação de produto.
* **Gestão de Vendas:**
    * [⏳] Endpoint para criação de Pedidos de Venda.
    * Lógica de negócio para validação e baixa de estoque durante a venda.
* **Padrão DTO:** Utilização de Data Transfer Objects para garantir uma API segura, desacoplada e otimizada.

## 🛠️ Tecnologias Utilizadas

* **Linguagem:** Java 17
* **Framework:** Spring Boot 3
* **Módulos Spring:** Spring Web, Spring Data JPA, Spring Boot DevTools
* **Persistência:** Hibernate
* **Banco de Dados:** SQL Server
* **Build Tool:** Maven
* **Outras Bibliotecas:** Lombok

## 🚀 Como Rodar o Projeto Localmente

Siga os passos abaixo para configurar e executar o projeto no seu ambiente de desenvolvimento.

### Pré-requisitos

* JDK 17 ou superior
* Maven 3.8 ou superior
* Uma instância do SQL Server rodando
* Sua IDE de preferência (IntelliJ, VS Code, Eclipse/STS)
* Uma ferramenta para testes de API, como [Insomnia](https://insomnia.rest/) ou [Postman](https://www.postman.com/).

### Configuração

1.  **Clone o repositório:**
    ```bash
    git clone [https://github.com/](https://github.com/)[seu-usuario]/[seu-repositorio].git
    cd [seu-repositorio]
    ```

2.  **Crie o Banco de Dados:**
    * No seu SQL Server, crie um banco de dados vazio. O nome sugerido é `erp_db`.

3.  **Configure as Credenciais:**
    * Na pasta `src/main/resources`, encontre o arquivo `application-dev.properties`.
    * **Este arquivo não deve ser commitado no Git!** Ele está no seu `.gitignore`.
    * Altere as propriedades abaixo com as suas credenciais do SQL Server:
        ```properties
        spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=erp_db;encrypt=false;trustServerCertificate=true
        spring.datasource.username=[seu_usuario_do_banco]
        spring.datasource.password=[sua_senha_do_banco]
        ```

4.  **Primeira Execução (Criar as Tabelas):**
    * No arquivo `application-dev.properties`, garanta que a linha abaixo esteja configurada como `create`:
        ```properties
        spring.jpa.hibernate.ddl-auto=create
        ```
    * Isso irá apagar e recriar todas as tabelas com base nas suas entidades Java.

5.  **Rode a Aplicação:**
    * Abra o projeto na sua IDE.
    * Encontre a classe principal (`ErpCoreSystemApplication.java`) e execute-a.
    * Alternativamente, rode pelo terminal na raiz do projeto:
        ```bash
        mvn spring-boot:run
        ```

6.  **Ajuste para Desenvolvimento:**
    * Após a primeira execução bem-sucedida, pare a aplicação.
    * Mude a linha no `application-dev.properties` de volta para `update`:
        ```properties
        spring.jpa.hibernate.ddl-auto=update
        ```
    * Isso evitará que seus dados de teste sejam apagados a cada reinicialização.

## Endpoints da API (Principais Rotas)

| Método | URL                               | Descrição                                         |
| :----- | :-------------------------------- | :-------------------------------------------------- |
| `POST` | `/api/clientes`                   | Cria um novo cliente.                               |
| `GET`  | `/api/clientes/{id}`              | Busca um cliente pelo seu ID.                       |
| `POST` | `/api/produtos`                   | Cria um novo produto com suas variações.            |
| `GET`  | `/api/produtos?idEmpresa=1`       | Lista todos os produtos de uma empresa.             |
| `POST` | `/api/fornecedores`               | Cria um novo fornecedor.                            |
| `GET`  | `/api/fornecedores?idEmpresa=1`   | Lista todos os fornecedores de uma empresa.         |
| `POST` | `/api/pedidos-venda`              | Cria um novo pedido de venda, com baixa de estoque. |

## 🗺️ Próximos Passos (Roadmap)

* [✅] **Fase 1: CRUDs Genéricos** (Cliente, Produto, Fornecedor)
* [⏳] **Fase 2: Lógica de Negócio Essencial** (Finalizar Pedido de Venda, Gestão de Estoque, Geração de Contas a Receber)
* [🔲] **Fase 3: Segurança e Validações** (Implementar autenticação com JWT, autorização por cargos, validação de DTOs)

---
*Este projeto está sendo desenvolvido com a ajuda do Gemini.*