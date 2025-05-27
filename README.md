# Aluno Online - Backend

Sistema de gerenciamento acadêmico para alunos, professores, disciplinas e matrículas, desenvolvido em Java com Spring Boot.

## Sumário

- [Sobre o Projeto](#sobre-o-projeto)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Como Executar](#como-executar)
- [Banco de Dados](#banco-de-dados)
- [Documentação da API (Swagger)](#documentação-da-api-swagger)
- [Exemplos de Uso](#exemplos-de-uso)
  - [Alunos](#alunos)
  - [Professores](#professores)
  - [Disciplinas](#disciplinas)
  - [Matrícula](#matrícula)
- [Scripts Úteis](#scripts-úteis)
- [Observações](#observações)

---

## Sobre o Projeto

Este projeto é uma API RESTful para gerenciamento de alunos, professores, disciplinas e matrículas. Permite criar, listar, atualizar e deletar registros, além de emitir históricos escolares, controlar o status das matrículas e garantir regras de negócio como impedir alterações não autorizadas.

## Tecnologias Utilizadas

- Java 17
- Spring Boot 3.4.5
- Spring Data JPA
- PostgreSQL
- Lombok
- Swagger/OpenAPI
- Maven

## Como Executar

1. **Clone o repositório:**
   ```sh
   git clone <url-do-repo>
   cd spring-back
   ```

2. **Configure o banco de dados:**
   - Certifique-se de ter um banco PostgreSQL rodando.
   - Altere as configurações em [`src/main/resources/application.properties`](src/main/resources/application.properties) se necessário.

3. **Instale as dependências e rode o projeto:**
   ```sh
   ./mvnw spring-boot:run
   ```
   Ou, no Windows:
   ```sh
   mvnw.cmd spring-boot:run
   ```

4. **Acesse a documentação Swagger:**
   - [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

## Banco de Dados

- O projeto utiliza PostgreSQL.
- Exemplo de configuração:
  ```
  spring.datasource.url=jdbc:postgresql://localhost:5432/aluno_online_p3b
  spring.datasource.username=postgres
  spring.datasource.password=123
  ```

## Documentação da API (Swagger)

A documentação interativa está disponível em:  
[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

---

## Exemplos de Uso

### Alunos

- **Criar aluno:**  
  <img src="assets/images/aluno-criar.png" alt="Criar aluno" width="600">

- **Atualizar aluno:**  
  <img src="assets/images/aluno-atualizar.png" alt="Atualizar aluno" width="600">

- **Deletar aluno:**  
  <img src="assets/images/aluno-deletar.png" alt="Deletar aluno" width="600">

- **Listar aluno por ID:**  
  <img src="assets/images/aluno-listar-por-id.png" alt="Listar aluno por ID" width="600">

- **Listar todos os alunos:**  
  <img src="assets/images/aluno-listar-todos.png" alt="Listar todos os alunos" width="600">

### Professores

- **Criar professor:**  
  <img src="assets/images/professor-criar.png" alt="Criar professor" width="600">

- **Atualizar professor:**  
  <img src="assets/images/professor-atualizar.png" alt="Atualizar professor" width="600">

- **Listar professores:**  
  <img src="assets/images/professor-listar-todos.png" alt="Listar professores" width="600">

- **Deletar professor:**  
  <img src="assets/images/professor-deletar.png" alt="Deletar professor" width="600">

### Disciplinas

- **Criar disciplina:**  
  <img src="assets/images/disciplina-criar.png" alt="Criar disciplina" width="600">

- **Evita aluno impor nota em matrícula sem permissão:**  
  <img src="assets/images/evita-aluno-impor-nota-matricula.png" alt="Evita aluno impor nota" width="600">
  > Esta funcionalidade impede que um aluno atualize as notas de uma matrícula caso não esteja autorizado, garantindo a integridade das avaliações.

- **Atualizar disciplina:**  
  <img src="assets/images/disciplina-atualizar.png" alt="Atualizar disciplina" width="600">

- **Deletar disciplina:**  
  <img src="assets/images/disciplina-deletar.png" alt="Deletar disciplina" width="600">

- **Listar disciplina por ID:**  
  <img src="assets/images/disciplina-listar-por-id.png" alt="Listar disciplina por ID" width="600">

- **Listar todas as disciplinas:**  
  <img src="assets/images/disciplina-listar-todas.png" alt="Listar todas as disciplinas" width="600">

- **Listar disciplinas por professor:**  
  <img src="assets/images/disciplina-listar-por-prof.png" alt="Listar disciplinas por professor" width="600">

### Matrícula

- **Criar matrícula:**  
  <img src="assets/images/matricula-criar.png" alt="Criar matrícula" width="600">
  
- **Erro ao matricular aluno já MATRICULADO ou APROVADO:**  
  <img src="assets/images/matricula-aluno-ja-matriculado.png" alt="Erro ao matricular aluno já MATRICULADO ou APROVADO" width="600">
  > Impede criar matricula de aluno com status MATRICULADO ou APROVADO.

- **Trancar matrícula:**  
  <img src="assets/images/matricula-trancar.png" alt="Trancar matrícula" width="600">

- **Atualizar notas da matrícula:**  
  <img src="assets/images/matricula-atualizar-nota.png" alt="Atualizar notas da matrícula" width="600">

- **Erro ao atualizar nota sem status MATRICULADO:**  
  <img src="assets/images/matricula-atualizar-nota-sem-status-matricula.png" alt="Erro ao atualizar nota sem status MATRICULADO" width="600">
  > Impede atualizar notas se a matrícula não estiver com status MATRICULADO.

- **Emitir histórico escolar:**  
  <img src="assets/images/matricula-exibir-historico.png" alt="Emitir histórico escolar" width="600">

---


## Endpoints Principais

### Alunos

- `POST /alunos` - Criar aluno
- `GET /alunos` - Listar todos os alunos
- `GET /alunos/{id}` - Buscar aluno por ID
- `PUT /alunos/{id}` - Atualizar aluno
- `DELETE /alunos/{id}` - Deletar aluno

### Professores

- `POST /professores` - Criar professor
- `GET /professores` - Listar todos os professores
- `GET /professores/{id}` - Buscar professor por ID
- `PUT /professores/{id}` - Atualizar professor
- `DELETE /professores/{id}` - Deletar professor

### Disciplinas

- `POST /disciplinas` - Criar disciplina
- `GET /disciplinas` - Listar todas as disciplinas
- `GET /disciplinas/{id}` - Buscar disciplina por ID
- `PUT /disciplinas/{id}` - Atualizar disciplina
- `DELETE /disciplinas/{id}` - Deletar disciplina
- `GET /disciplinas/professor/{professorId}` - Listar disciplinas por professor

### Matrícula

- `POST /matriculas` - Criar matrícula
- `PATCH /matriculas/trancar/{id}` - Trancar matrícula
- `PATCH /matriculas/atualizar-notas/{id}` - Atualizar notas da matrícula
- `GET /matriculas/historico/{alunoId}` - Emitir histórico escolar do aluno

---

## Scripts Úteis

- **Script para criar pessoas via Postman:**  
  <img src="assets/images/scriptCriarPessoa.png" alt="Script criar pessoa" width="600">

---

## Observações

- Antes de excluir um professor, é necessário remover as disciplinas associadas a ele.
- O projeto segue boas práticas REST e utiliza DTOs para requisições e respostas.
- Algumas operações possuem regras de negócio para garantir integridade, como impedir atualização de notas por usuários não autorizados ou impedir matrícula duplicada.

---

Desenvolvido para fins acadêmicos.