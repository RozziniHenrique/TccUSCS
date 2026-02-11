# 💇‍♀️ STEFER API

API para gestão de agendamentos desenvolvida com **Java 21** e **Spring Boot 3**. Este projeto foca em boas práticas de segurança e arquitetura REST.

## 🛠️ Tecnologias
- **Java 21** & **Spring Boot 3**
- **Spring Security 6** & **JWT** (Autenticação Stateless)
- **BCrypt** (Criptografia de senhas)
- **MySQL** (Banco de dados)
- **Hibernate/JPA** (Persistência)
- **Maven** (Gerenciador de dependências)

## 🔐 Segurança Implementada
A API utiliza autenticação via Token JWT. Para acessar as rotas protegidas (como Agendamentos), é necessário:
1. Realizar o cadastro em `/usuarios`.
2. Realizar o login em `/login` para obter o Token.
3. Enviar o Token no Header da requisição: `Authorization: Bearer <seu_token>`.

## 🚀 Como Executar
1. Clone o repositório.
2. Configure o banco MySQL no `application.properties`.
3. Execute o comando: `./mvnw spring-boot:run`
