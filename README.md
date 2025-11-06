#  OsFacil API: Sistema de Gerenciamento de Ordens de Serviço para Automóveis

O **OsFacil API** é uma  API RESTful desenvolvida em Spring Boot, focada no **Gerenciamento de Ordens de Serviço (OS)** para o setor automotivo. A aplicação permite a administração completa de veículos, produtos, clientes, funcionários e o ciclo de vida das ordens de serviço.

**🔗 Link da API Publicada:** (https://osfacil.onrender.com)

## Funcionalidades Principais

* **Gerenciamento Completo de Cadastros:** CRUD (Criação, Leitura, Atualização, Deleção) para **Veículos**, **Produtos**, **Clientes** e **Funcionários**.
* **Controle de Ordem de Serviço:** Criação, acompanhamento de status e finalização de **Ordens de Serviço**.
* **Autenticação Segura (JWT):** Clientes e usuários podem realizar login, gerando um **JSON Web Token (JWT)** para acesso seguro aos recursos da API 
* **Design RESTful Avançado (HATEOAS):** As respostas da API incluem *links* de hipermídia, guiando o cliente sobre as ações possíveis e navegabilidade.

---

## Tecnologias e Dependências

O projeto é construído com base nas seguintes tecnologias e bibliotecas-chave, conforme o `pom.xml`:

| Tecnologia/Dependência | Grupo ID | Utilidade |
| :--- | :--- | :--- |
| **Spring Boot Web & JPA** | `org.springframework.boot` | Base para a API REST e Persistência de Dados. |
| **Oracle JDBC** | `com.oracle.database.jdbc` | Conexão com o Banco de Dados Oracle (Produção). |
| **Java JWT** | `com.auth0` | Geração e Validação de Tokens JWT para autenticação. |
| **HATEOAS** | `org.springframework.boot` | Adição de Hipermídia (links) às respostas da API. |
| **Dotenv-Java** | `io.github.cdimascio` | Carregamento seguro de Variáveis de Ambiente (`.env`). |
| **Lombok** | `org.projectlombok` | Redução do código *boilerplate* (getters, setters, etc.). |
| **Spring Boot Devtools** | `org.springframework.boot` | Recarregamento automático da aplicação durante o desenvolvimento. |

---

## Como Iniciar o Projeto
Siga os passos abaixo para clonar, configurar e rodar a API localmente.

### Pré-requisitos
Certifique-se de ter instalado em sua máquina:

* **Java Development Kit (JDK) 21** ou superior.
* **Maven** (Gerenciador de Dependências) ou utilize o `mvnw` (Maven Wrapper) do projeto.
* **Git** para clonar o repositório.

### 1. Clonar o Repositório

git clone https://github.com/renatosgk/Os-Facil--sprint2.git

### 2.Entrar na pasta do projeto

cd Os-Facil--sprint2

### 3.Rodar o projeto
./mvnw spring-boot:run







