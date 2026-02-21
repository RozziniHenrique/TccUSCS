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

### ✍️ Como rodar o projeto
* **Pré-requisitos**
    Ter o Java JDK 17+ instalado.

    Ter o Node.js e o npm instalados.

    Ter o MySQL rodando.

* **Configuração do Banco de Dados**
    Clone o repositório.

    Crie o banco chamado stefer no seu MySQL.

    Altere as credenciais (seu usuário e senha do MySQL) em:
api -> src -> main -> resources -> application.properties

spring.datasource.url=jdbc:mysql://localhost:3306/stefer?useSSL=false&serverTimezone=America/Sao_Paulo

spring.datasource.username=seu_usuario

spring.datasource.password=sua_senha

* **Rodando o Back-end (Spring Boot)**
    Abra a pasta api no IntelliJ ou VS Code.

    Execute o comando: ./mvnw spring-boot:run (ou dê Play na classe SteferApplication.java).

    O servidor subirá em http://localhost:8080.

    Dica: Acesse http://localhost:8080/swagger-ui.html para testar os endpoints.