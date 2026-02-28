# 💈 Projeto STEFER - Barber Management System

API de alto desempenho para gestão de barbearias, focada em inteligência de negócios, segurança de dados e automação de regras de agendamento.

---

### 🚀 Novidades da Versão 2.0
* **Business Intelligence:** Dashboard integrado com faturamento em tempo real, ranking de performance e alertas de qualidade (notas < 4.0).
* **Arquitetura Clean (DDD):** Refatoração completa para o padrão de domínios, isolando regras de negócio de infraestrutura para maior escalabilidade.
* **Relatórios Dinâmicos:** Estatísticas de serviços mais procurados por especialidade e performance de funcionários.

---

### 🛠️ Stack Tecnológica
* **Backend:** Java 21 + Spring Boot 3
* **Segurança:** Spring Security + JWT (JSON Web Token)
* **Persistência:** MySQL + Flyway (Migrations)
* **Documentação:** Swagger (SpringDoc)
* **Qualidade:** JUnit 5 + Mockito

---

### 📂 Arquitetura do Projeto
O projeto utiliza uma estrutura organizada por contextos de domínio para garantir baixa manutenção:

* `domain`: Contém as **Entities**, **Repositories**, **DTOs** e **Validadores** (ex: `agendamento`, `funcionario`, `cliente`).
* `controller`: Camada REST para exposição dos endpoints.
* `infra`: Configurações globais, como segurança JWT e tratamento de exceções customizadas.

---

### 📏 Regras de Negócio & Validações
O sistema utiliza o padrão **Strategy** para aplicar regras automáticas:
* **Horário de Funcionamento:** Segunda a Sábado, das 07h às 19h.
* **Antecedência:** Mínimo de 30 min para agendar e 2h para cancelar.
* **Prevenção de Conflitos:** Validação de duplicidade de horário para barbeiro e cliente.
* **Gestão de Qualidade:** Alertas para colaboradores com média de avaliação inferior a 4.0.

---

### 📊 Principais Endpoints
| Método | Endpoint | Descrição |
| :--- | :--- | :--- |
| **GET** | `/dashboard` | Retorna faturamento do dia, ranking de funcionários e alertas. |
| **POST** | `/agendamentos` | Cria agendamento validando todas as regras de negócio. |
| **PUT** | `/agendamentos/finalizar` | Conclui o serviço e registra a nota (atualiza faturamento). |
| **GET** | `/relatorio/estatisticas` | Mostra quais especialidades são as mais rentáveis. |

---

### ✍️ Como rodar o projeto
1. **Banco de Dados:** Crie um banco MySQL chamado `stefer`.
2. **Configuração:** Ajuste as credenciais em `src/main/resources/application.properties`.
3. **Execução:** ```bash
   ./mvnw spring-boot:run