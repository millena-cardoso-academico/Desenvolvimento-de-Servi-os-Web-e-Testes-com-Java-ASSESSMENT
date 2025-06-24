# Projeto de API REST com Javalin - Sistema de Gerenciamento de Tarefas

Este projeto, desenvolvido como parte da avaliação de Desenvolvimento de Serviços Web e Testes com Java, consiste em uma API REST para gerenciamento de tarefas. A aplicação foi construída com o framework Javalin, com foco em clareza, manutenibilidade e cobertura de testes.

**Por:** Millena Cardoso Sales Santos

## Tecnologias Utilizadas

* **Java 23**
* **Javalin 6.1.3**: Framework web para criação da API.
* **Gradle**: Ferramenta de automação de build.
* **JUnit 5**: Framework para testes unitários e de integração.
* **AssertJ**: Biblioteca para asserções fluentes nos testes.
* **Jackson**: Biblioteca para serialização/desserialização de JSON.

## Como Executar

1.  **Executar a Aplicação (Servidor):**
    Rode a classe `com.empresa.App` através da sua IDE ou via Gradle com o comando. O servidor iniciará na porta `7000`.
    ```bash
    ./gradlew run
    ```

2.  **Executar os Testes Automatizados:**
    Rode os testes pela sua IDE ou via Gradle. Eles validam todos os endpoints e regras de negócio.
    ```bash
    ./gradlew test
    ```

3.  **Executar o Cliente Manual de Demonstração:**
    Para consumir a API manualmente, rode a classe `com.empresa.client.HttpClient`. Certifique-se de que o servidor (`App`) esteja em execução.

## Endpoints da API

A API implementa todos os endpoints genéricos e o caso de uso principal (Gerenciamento de Tarefas) definidos nos requisitos.

### Endpoints Gerais

* **`GET /hello`**
    * Descrição: Retorna uma saudação simples, usada para o teste inicial da aplicação.
    * Resposta (200 OK): `Hello, Javalin!`

* **`GET /status`**
    * Descrição: Retorna o status da aplicação e o timestamp atual no formato ISO-8601.
    * Resposta (200 OK):
        ```json
        {
          "status": "ok",
          "timestamp": "2025-06-25T07:30:00.123Z"
        }
        ```

* **`POST /echo`**
    * Descrição: Retorna o mesmo corpo JSON que foi enviado na requisição.
    * Corpo da Requisição e Resposta (200 OK):
        ```json
        {
            "chave": "valor"
        }
        ```

* **`GET /saudacao/{nome}`**
    * Descrição: Retorna uma mensagem de saudação personalizada.
    * Resposta (200 OK) para `/saudacao/Mundo`:
        ```json
        {
            "mensagem": "Olá, Mundo!"
        }
        ```

### Endpoints de Tarefas (Caso de Uso Principal)

* **`POST /tarefas`**
    * Descrição: Cria uma nova tarefa. O título é obrigatório.
    * Corpo da Requisição:
        ```json
        {
          "titulo": "Revisar código Java",
          "descricao": "Verificar a lógica da API"
        }
        ```
    * Resposta (201 Created):
        ```json
        {
          "id": 1,
          "titulo": "Revisar código Java",
          "descricao": "Verificar a lógica da API",
          "concluida": false
        }
        ```
    * Resposta de Erro (400 Bad Request):
        ```json
        {
          "erro": "O título é obrigatório"
        }
        ```

* **`GET /tarefas`**
    * Descrição: Lista todas as tarefas cadastradas.
    * Resposta (200 OK):
        ```json
        [
          {
            "id": 1,
            "titulo": "Tarefa 1",
            "descricao": "...",
            "concluida": false
          }
        ]
        ```

* **`GET /tarefas/{id}`**
    * Descrição: Busca uma tarefa pelo seu ID.
    * Resposta (200 OK):
        ```json
        {
          "id": 1,
          "titulo": "Tarefa 1",
          "descricao": "...",
          "concluida": false
        }
        ```
    * Resposta de Erro (404 Not Found): Se a tarefa não existir.
        ```json
        {
          "erro": "Tarefa com id 99 não encontrada"
        }
        ```

## Evidências da Execução

Abaixo estão as capturas de tela que comprovam o funcionamento da aplicação, a execução dos testes e o consumo dos endpoints.

* **Servidor em execução:**
    * ![servidor rodando](https://github.com/user-attachments/assets/c3bb5c1c-8c2c-4ecd-8962-80189a4e173a)
* **Testes automatizados aprovados:**
    * ![testes aprovados](https://github.com/user-attachments/assets/86334947-53c1-4d54-9dbb-e32ff554a09d)
* **Demonstração com Postman:**
    * **`POST /tarefas` (Sucesso):** ![teste com postman](https://github.com/user-attachments/assets/9c7035f7-be05-4637-8738-ecff3cb0ccc4) ![segundo teste com o postman](https://github.com/user-attachments/assets/74926294-b529-4514-b55f-a2df1eb32724)
    * **`POST /tarefas` (Erro 400 - Campo Incompleto):** ![teste de erro com postman](https://github.com/user-attachments/assets/089becba-65cd-4d37-a958-0e09e74a4935)
    * **`GET /tarefas`:** ![Metodo GET para listar as tarefas](https://github.com/user-attachments/assets/fe6a8654-ef4c-45ff-8eeb-e70627395d2c)
    * **`GET /tarefas/{id}` (Sucesso):** ![Listando tarefa por ID TESTE POSTMAN](https://github.com/user-attachments/assets/cc9f915e-94f7-4ed8-87b0-3f8550d6ec1b)
    * **`GET /tarefas/{id}` (Erro 404):** ![Buscando tarefa com id 99 GET COM ERRO 404](https://github.com/user-attachments/assets/447d5e64-3ce5-4c9d-b1a2-5f532517585d)
    * **`GET /status`:** ![obter status da aplicação GET](https://github.com/user-attachments/assets/fb55f164-ba57-492e-a2e5-51929c44e3d9)
