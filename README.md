O STEFER é uma API REST desenvolvida com Spring Boot 3 para gestão de agendamentos em estabelecimentos de estética/beleza. O sistema destaca-se pela sua inteligência na alocação de profissionais e rigorosas validações de regras de negócio.

🛠️ Tecnologias Utilizadas
Java 25 (OpenJDK)

Spring Boot 3.5.7

Spring Data JPA

MySQL 8.0

Flyway Migration (Versionamento de banco de dados)

Lombok

Validation (Bean Validation)

Jackson (Gestão de Timezones e Datas)

🧠 Regras de Negócio e Funcionalidades
📅 Agendamento
O sistema permite marcar serviços validando diversos critérios simultaneamente:

Alocação Inteligente: Se um profissional não for informado, o sistema seleciona automaticamente um profissional aleatório que esteja ativo e livre no horário solicitado.

Prevenção de Conflitos:

Um profissional não pode ter dois agendamentos no mesmo horário.

Um cliente não pode ter dois agendamentos no mesmo horário.

Horário de Funcionamento:

Segundas a Sábados, das 07:00 às 19:00.

Último horário de início permitido: 18:00 (considerando 1h de duração).

Antecedência Mínima: Agendamentos devem ser feitos com no mínimo 30 minutos de antecedência.

👥 Gestão de Entidades
Clientes e Funcionários: Cadastro completo com endereço e suporte a Exclusão Lógica (campo ativo).

Especialidades: Vinculação obrigatória para garantir que o serviço correto seja prestado.

🚦 Como rodar o projeto
1. Requisitos
MySQL rodando localmente.

Java 25 instalado.

2. Configuração do Banco
Crie o banco de dados no MySQL:

SQL

CREATE DATABASE stefer;
3. Ajuste o application.properties
Certifique-se de que o fuso horário está configurado para Brasília:

Properties

spring.datasource.url=jdbc:mysql://localhost:3306/stefer?serverTimezone=America/Sao_Paulo
spring.jackson.time-zone=America/Sao_Paulo
📡 Principais Endpoints
Agendamentos
POST /agendamentos - Realiza um novo agendamento.

GET /agendamentos - Lista todos os agendamentos de forma paginada e ordenada por data.

Exemplo de JSON para Agendamento:

JSON

{
    "idFuncionario": null, 
    "idCliente": 1,
    "idEspecialidade": 1,
    "data": "2026-05-20T14:00:00"
}
(Se idFuncionario for null, o sistema escolherá um profissional disponível automaticamente).

🏗️ Estrutura de Pastas
controller: Endpoints da API.

model: Entidades JPA e Repositories.

service: Lógica de negócio e validações (Service Layer).

dto: Records para transferência de dados e proteção da API.

infra: Configurações de segurança e tratador de erros.

Desenvolvido por Henrique R. - Projeto STEFER

🛠️ Próximos Passos (Roadmap)
O projeto continua em evolução. As próximas implementações previstas são:

🛡️ Tratamento de Exceções Global: Implementação de um @RestControllerAdvice para capturar erros de validação e regras de negócio, retornando mensagens amigáveis em JSON em vez de StackTraces.

🚫 Cancelamento de Agendamentos: Criação de funcionalidade para cancelamento com regra de antecedência mínima de 24 horas.

📄 Comprovante de Agendamento: Evolução do retorno do POST para devolver um DTO detalhado com nomes de cliente, funcionário e especialidade.

📊 Relatórios e Dashboards: Endpoints para consulta de produtividade por funcionário e histórico de frequência de clientes.

🔐 Segurança (Spring Security): Implementação de autenticação via JWT para proteger os endpoints da API.
