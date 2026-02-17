# 🏥 Projeto STEFER

API de alto desempenho para gestão de agendamentos clínicos, focada em segurança de dados e automação de regras de negócio.

---

### 🛠️ Stack Tecnológica
* **Backend:** Java 21 + Spring Boot 3
* **Persistência:** MySQL (Produção) | H2 (Testes)
* **Qualidade:** JUnit 5 + Mockito

---

### 🛡️ Escudo de Testes
Implementamos uma suíte de testes rigorosa para garantir que a clínica nunca pare por erros de lógica.

| Tipo de Teste | O que protegemos? | Qtd |
| :--- | :--- | :---: |
| **Unitários** | Regras de Horário, Antecedência e Conflitos | 15 |
| **Integração** | Endpoints da API e Persistência de Dados | 4 |

---

### 📏 Regras de Negócio (Hardcoded)
Para garantir o funcionamento perfeito, o sistema valida automaticamente:

* **Relógio Clínico:** Agendamentos apenas de Seg a Sáb (07h - 19h).
* **Reserva:** Mínimo de 30 minutos de antecedência para marcar.
* **Rescisão:** Mínimo de 2 horas de antecedência para cancelar.
* **Fidelidade:** Verificação de duplicidade para Funcionário e Cliente.

---

### 🚀 Comando para validar o projeto:
```bash
mvn test