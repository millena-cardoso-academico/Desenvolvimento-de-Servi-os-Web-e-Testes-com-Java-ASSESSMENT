package com.empresa;

import io.javalin.testtools.JavalinTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AppTest {


    // Implementado o BeforeEach para limpar os dados antes de cada teste e garantir isolamento
    @BeforeEach
    void setUp() {
        App.resetStateForTests();
    }

    // Testa o endpoint GET /hello, verificando a resposta padrão.
    @Test
    void testHelloEndpoint() {
        JavalinTest.test(App.criarApp(), (server, client) -> {
            var res = client.get("/hello");
            assertThat(res.code()).isEqualTo(200);
            assertThat(res.body().string()).isEqualTo("Hello, Javalin!");
        });
    }

    // Testa a criação de uma tarefa (POST /tarefas) com dados válidos.
    @Test
    void createTaskTest() {
        JavalinTest.test(App.criarApp(), (server, client) -> {
            String novaTarefaJson = """
            {
              "titulo": "Estudar Javalin",
              "descricao": "Preparar para a reentrega do AT"
            }
            """;
            var res = client.post("/tarefas", novaTarefaJson);
            assertThat(res.code()).isEqualTo(201);
            String body = res.body().string();
            assertThat(body).contains("Estudar Javalin");
            assertThat(body).contains("\"id\":1");
            assertThat(body).contains("\"concluida\":false");
        });
    }

    // Testa a validação na criação de uma tarefa sem título, esperando erro 400.
    @Test
    void testCreateTaskFailsWithNoTitle() {
        JavalinTest.test(App.criarApp(), (server, client) -> {
            String tarefaInvalidaJson = "{\"descricao\": \"Essa tarefa não tem título\"}";
            var res = client.post("/tarefas", tarefaInvalidaJson);
            assertThat(res.code()).isEqualTo(400);
            assertThat(res.body().string()).contains("O título é obrigatório");
        });
    }

    // Testa a busca por uma tarefa específica e existente (GET /tarefas/{id}).
    @Test
    void findTaskByIdTest() {
        JavalinTest.test(App.criarApp(), (server, client) -> {
            // Primeiro, cria uma tarefa para poder buscar
            String tarefaJson = "{\"titulo\": \"Tarefa para buscar\"}";
            client.post("/tarefas", tarefaJson); // Esta será a tarefa de ID 1

            var res = client.get("/tarefas/1");
            assertThat(res.code()).isEqualTo(200);
            assertThat(res.body().string()).contains("Tarefa para buscar");
        });
    }

    // Testa a busca por uma tarefa com ID inexistente, esperando erro 404.
    @Test
    void findTaskByIdNotFoundTest() {
        JavalinTest.test(App.criarApp(), (server, client) -> {
            var res = client.get("/tarefas/999");
            assertThat(res.code()).isEqualTo(404);
            assertThat(res.body().string()).contains("não encontrada");
        });
    }

    // Testa a listagem de todas as tarefas (GET /tarefas) após criar algumas.
    @Test
    void listTasksTest() {
        JavalinTest.test(App.criarApp(), (server, client) -> {
            client.post("/tarefas", "{\"titulo\": \"Primeira Tarefa\"}");
            client.post("/tarefas", "{\"titulo\": \"Segunda Tarefa\"}");

            var res = client.get("/tarefas");
            assertThat(res.code()).isEqualTo(200);
            String body = res.body().string();
            assertThat(body).contains("Primeira Tarefa");
            assertThat(body).contains("Segunda Tarefa");
        });
    }
}