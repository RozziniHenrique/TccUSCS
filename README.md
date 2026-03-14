# 💈 Projeto de Estudo - Gestão de Franquias de Beleza

API de alto desempenho para **Gestão de Franquias** e centros de estética, focada em inteligência de negócios (BI), CRM de retenção e automação de agendamentos.

---

### 🚀 Novidades da Versão 2.0 (Atualizado)

- **CRM & Retenção de Clientes:** Algoritmo que monitora a última visita e sinaliza clientes em risco (inativos há +15 ou +30 dias) com alertas visuais.
- **Módulo PDV (Atendimento Balcão):** Fluxo simplificado para registro de vendas rápidas sem necessidade de agendamento prévio.
- **Business Intelligence:** Dashboard avançado com faturamento diário, Ticket Médio por cliente e ranking de demanda por serviço.
- **Arquitetura Clean (DDD):** Refatoração completa para o padrão de domínios, isolando regras de negócio de infraestrutura.

---

### 🛠️ Stack Tecnológica

- **Backend:** Java 21 + Spring Boot 3 (Spring Security + JWT)
- **Frontend Web:** React + Vite + Tailwind CSS / Styled Components
- **Mobile:** React Native (App Cliente - _Em espera_)
- **Persistência:** MySQL + Flyway
- **Qualidade:** JUnit 5 + Mockito

---

### 📂 Estrutura do Ecossistema

- **`/api`**: Core do sistema, regras de negócio e inteligência de dados.
- **`/web`**: Painel administrativo para gestão de franqueados, profissionais e controle de retenção de clientes.
- **`/mobile`**: Interface do cliente para agendamentos e acompanhamento. -> _Desenvolvimento em espera_

---

### 📏 Regras de Negócio & Inteligência

- **Gestão de Retenção:** Classificação visual automática (Verde: Em dia | Amarelo: 15 dias ausente | Vermelho: +30 dias ausente).
- **LTV (Lifetime Value):** Rastreamento de gasto acumulado por cliente para identificação de perfis VIP.
- **Prevenção de Conflitos:** Validação de duplicidade de horário para profissional e cliente.
- **Antecedência:** Mínimo de 30 min para agendar e 2h para cancelar.

---

### 📊 Principais Endpoints

| Método   | Endpoint                      | Descrição                                                       |
| :------- | :---------------------------- | :-------------------------------------------------------------- |
| **GET**  | `/dashboard`                  | Resumo de faturamento, ticket médio e pendências do dia.        |
| **GET**  | `/dashboard/relatorio-gastos` | Dados de LTV e histórico de última visita de todos os clientes. |
| **POST** | `/agendamentos`               | Registro de agendamento ou atendimento rápido (Balcão).         |
| **PUT**  | `/agendamentos/finalizar`     | Conclui serviço, registra nota e atualiza saúde financeira.     |

---

### ✍️ Como rodar o projeto

**Backend (API):**

1. Crie um banco MySQL chamado `erp_salao`.
2. Configure as credenciais em `api/src/main/resources/application.properties`.

```bash
cd api
./mvnw spring-boot:run
Frontend (Web):

Bash
cd web
npm install
npm run dev
```
