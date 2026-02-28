# 💈 Projeto STEFER - Barber Management System

API de alto desempenho para gestão de barbearias, focada em inteligência de negócios, segurança de dados e automação de regras de agendamento.

---

### 🚀 Novidades da Versão 2.0
* **Business Intelligence:** Dashboard integrado com faturamento em tempo real, ranking de performance e alertas de qualidade (notas < 4.0).
* **Arquitetura Clean (DDD):** Refatoração completa para o padrão de domínios, isolando regras de negócio de infraestrutura para maior escalabilidade.
* **Relatórios Dinâmicos:** Estatísticas de serviços mais procurados por especialidade e performance de funcionários.

---

### 🛠️ Stack Tecnológica
* **Backend:** Java 21 + Spring Boot 3 (Spring Security + JWT)
* **Frontend Web:** React + Vite + Tailwind CSS (Gestão)
* **Mobile:** React Native (App Cliente)
* **Persistência:** MySQL + Flyway
* **Qualidade:** JUnit 5 + Mockito

---

### 📂 Estrutura do Ecossistema
O projeto está dividido em frentes específicas para atender diferentes perfis de usuário:

* **`/api`**: Core do sistema, regras de negócio e inteligência de dados.
* **`/web`**: Painel administrativo para donos de barbearia e barbeiros (Faturamento, Relatórios). -> Proximo a ser desenvolvido
* **`/mobile`**: Interface do cliente para agendamentos e acompanhamento de serviços. -> Desenvolvimento em espera

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
