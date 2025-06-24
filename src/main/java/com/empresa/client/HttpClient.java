package com.empresa.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpClient {
    // CORRIGIDO BASE URL DE 7001 PARA 7000 conforme exercício
    private static final String BASE_URL = "http://localhost:7000";

    // Envia uma requisição POST para criar uma nova tarefa na API.
    public static void criarTarefa(String jsonTarefa) throws Exception {
        URL url = new URL(BASE_URL + "/tarefas");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            os.write(jsonTarefa.getBytes());
            os.flush();
        }

        System.out.println("POST /tarefas -> HTTP " + conn.getResponseCode());
        imprimirResposta(conn);
        conn.disconnect();
    }

    // Envia uma requisição GET para obter a lista de todas as tarefas.
    public static void listarTarefas() throws Exception {
        URL url = new URL(BASE_URL + "/tarefas");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        System.out.println("GET /tarefas -> HTTP " + conn.getResponseCode());
        imprimirResposta(conn);
        conn.disconnect();
    }

    // Envia uma requisição GET para buscar uma tarefa específica pelo seu ID.
    public static void buscarTarefaPorId(long id) throws Exception {
        URL url = new URL(BASE_URL + "/tarefas/" + id);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        System.out.println("GET /tarefas/" + id + " -> HTTP " + conn.getResponseCode());
        imprimirResposta(conn);
        conn.disconnect();
    }

    // 4) GET /status — obtém status e timestamp
    public static void obterStatus() throws Exception {
        URL url = new URL("http://localhost:7000/status");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        int status = conn.getResponseCode();
        System.out.println("GET /status → HTTP " + status);

        try (var br = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        }
        conn.disconnect();
    }

    // Método auxiliar que lê e imprime a resposta (sucesso ou erro) de uma conexão.
    private static void imprimirResposta(HttpURLConnection conn) throws Exception {
        int status = conn.getResponseCode();
        try (var br = new BufferedReader(new InputStreamReader(
                status < 300 ? conn.getInputStream() : conn.getErrorStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        }
    }

    // Método principal que executa uma sequência de chamadas para demonstrar a API.
    public static void main(String[] args) throws Exception {
        System.out.println("--- Criando tarefas ---");
        String tarefa1 = """
            {"titulo": "Revisar código Java", "descricao": "Verificar a lógica da API"}
            """;
        String tarefa2 = """
            {"titulo": "Escrever README", "descricao": "Documentar os endpoints"}
            """;
        String tarefaInvalida = "{\"descricao\": \"Falta o título aqui\"}";

        criarTarefa(tarefa1);
        criarTarefa(tarefa2);
        criarTarefa(tarefaInvalida); // Testando a validação

        System.out.println("\n--- Listando todas as tarefas ---");
        listarTarefas();

        System.out.println("\n--- Buscando tarefa com ID 1 ---");
        buscarTarefaPorId(1);

        System.out.println("\n--- Buscando tarefa com ID 99 (não existe) ---");
        buscarTarefaPorId(99);

        System.out.println("\n--- Obtendo status da aplicação ---");
        obterStatus();
    }
}