# Projeto de API REST com Javalin - Sistema de Tarefas

Este projeto é uma avaliação de Desenvolvimento de Serviços Web e Testes com Java. Consiste em uma API REST para gerenciamento de tarefas, construída com o framework Javalin.

Por: Millena Cardoso Sales Santos

## Tecnologias Utilizadas

* **Java 23**
* **Javalin 6.1.3**: Framework web para criação da API.
* **Gradle**: Ferramenta de automação de build.
* **JUnit 5**: Framework para testes unitários e de integração.
* **AssertJ**: Biblioteca para asserções fluentes nos testes.
* **SLF4J**: Logging da aplicação.
* **Jackson**: Serialização/desserialização de JSON.

## Como Executar

1.  **Executar a Aplicação (Servidor):**
    Rode a classe `com.empresa.App` através da sua IDE ou via Gradle com o comando:
    ```bash
    ./gradlew run
    ```
    O servidor iniciará na porta `7000`.

2.  **Executar os Testes Automatizados:**
    Rode os testes pela sua IDE ou via Gradle com o comando:
    ```bash
    ./gradlew test
    ```

3.  **Executar o Cliente Manual:**
    Para consumir a API manualmente, rode a classe `com.empresa.client.HttpClient`. Certifique-se de que o servidor (`App`) esteja em execução.

## Endpoints da API

### Status
* **GET /status**: Retorna o status da aplicação e o timestamp atual.
    * **Resposta (200 OK):**
        ```json
        {
          "status": "ok",
          "timestamp": "2025-06-23T20:55:00.123Z"
        }
        ```

### Tarefas
* **POST /tarefas**: Cria uma nova tarefa.
    * **Corpo da Requisição:**
        ```json
        {
          "titulo": "Minha Nova Tarefa",
          "descricao": "Detalhes da tarefa."
        }
        ```
    * **Resposta (201 Created):**
        ```json
        {
          "id": 1,
          "titulo": "Minha Nova Tarefa",
          "descricao": "Detalhes da tarefa.",
          "concluida": false
        }
        ```
    * **Resposta de Erro (400 Bad Request):** Se o título não for fornecido.
        ```json
        {
          "erro": "O título é obrigatório"
        }
        ```

* **GET /tarefas**: Lista todas as tarefas.
    * **Resposta (200 OK):**
        ```json
        [
          {
            "id": 1,
            "titulo": "Tarefa 1",
            "descricao": "...",
            "concluida": false
          },
          {
            "id": 2,
            "titulo": "Tarefa 2",
            "descricao": "...",
            "concluida": true
          }
        ]
        ```

* **GET /tarefas/{id}**: Busca uma tarefa pelo seu ID.
    * **Resposta (200 OK):**
        ```json
        {
          "id": 1,
          "titulo": "Tarefa 1",
          "descricao": "...",
          "concluida": false
        }
        ```
    * **Resposta de Erro (404 Not Found):** Se a tarefa não existir.
        ```json
        {
          "erro": "Tarefa com id 1 não encontrada"
        }
        ```