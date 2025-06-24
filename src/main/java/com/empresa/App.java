package com.empresa;

import io.javalin.Javalin;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class App {
    // armazenamento em memória
    private static Map<Long, Tarefa> tarefas = new ConcurrentHashMap<>();
    private static AtomicLong proximoId = new AtomicLong(); // Gerador de ID

    // DTO (Record) de Tarefa. ID, título, descrição e status.
    public record Tarefa(Long id, String titulo, String descricao, boolean concluida) {}
    // DTO para a criação de tarefas, não precisa do ID, ja que será gerado.
    public record NovaTarefa(String titulo, String descricao) {}

    public static void resetStateForTests() {
        tarefas.clear();
        proximoId.set(0);
    }

    // fabrica do app para JavalinTest
    public static Javalin criarApp() {
        return Javalin.create()
                // endpoint GET simples
                .get("/hello", ctx -> ctx.result("Hello, Javalin!"))
                // endpoint GET status + timestamp
                .get("/status", ctx -> {
                    ctx.json(Map.of(
                            "status", "ok",
                            "timestamp", Instant.now().toString()
                    ));
                })
                // endpoint POST echo
                .post("/echo", ctx -> {
                    Map<String, String> body = ctx.bodyAsClass(Map.class);
                    ctx.json(body);
                })
                // endpoint GET saudação
                .get("/saudacao/{nome}", ctx -> {
                    String nome = ctx.pathParam("nome");
                    ctx.json(Map.of("mensagem", "Olá, " + nome + "!"));
                })
                // --- AQUI COMEÇAM AS MUDANÇAS ---
                .post("/tarefas", ctx -> {
                    NovaTarefa novaTarefa = ctx.bodyAsClass(NovaTarefa.class);

                    // VALIDAÇÃO: Título é obrigatório
                    if (novaTarefa.titulo() == null || novaTarefa.titulo().isBlank()) {
                        ctx.status(400).json(Map.of("erro", "O título é obrigatório"));
                        return; // Interrompe a execução
                    }

                    long id = proximoId.incrementAndGet();
                    Tarefa tarefaCompleta = new Tarefa(id, novaTarefa.titulo(), novaTarefa.descricao(), false);
                    tarefas.put(id, tarefaCompleta);
                    ctx.status(201).json(tarefaCompleta);
                })

                // 2. Endpoint GET /tarefas (listar todas)
                .get("/tarefas", ctx -> ctx.json(tarefas.values()))

                // 3. Endpoint GET /tarefas/{id} (buscar por ID)
                .get("/tarefas/{id}", ctx -> {
                    try {
                        Long id = Long.parseLong(ctx.pathParam("id"));
                        Tarefa tarefa = tarefas.get(id);

                        if (tarefa != null) {
                            ctx.json(tarefa);
                        } else {
                            // VALIDAÇÃO: 404 para ID não encontrado
                            ctx.status(404).json(Map.of("erro", "Tarefa com id " + id + " não encontrada"));
                        }
                    } catch (NumberFormatException e) {
                        // Tratamento de erro para ID inválido (não numérico)
                        ctx.status(400).json(Map.of("erro", "O ID da tarefa deve ser um número válido"));
                    }
                });
    }  // fecha criarApp

    // método main só para rodar manualmente
    public static void main(String[] args) {
        criarApp().start(7000);
    }

}
