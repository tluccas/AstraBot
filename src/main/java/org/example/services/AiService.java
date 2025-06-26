package org.example.services;

import com.google.gson.*;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.*;
import java.io.IOException;

public class AiService {

    private static final Dotenv dotenv = Dotenv.load();

    private static final String API_URL = dotenv.get("OPENROUTER_API_URL"); // API que irá ser usada (DeepSeek R1)
    private static final String MODEL = "deepseek/deepseek-r1-0528:free";
    private static final String API_KEY = dotenv.get("OPENROUTER_API_KEY"); // carregando a API do openrouter


    private final OkHttpClient client = new OkHttpClient();
    private final Gson gson = new Gson();

    // Método principal: envia uma pergunta para a IA e retorna a resposta
    public String obterResposta(String pergunta) {
        // Monta o corpo da requisição (JSON que será enviado)
        JsonObject body = new JsonObject();
        body.addProperty("model", MODEL);

        // Cria um array de mensagens
        JsonArray messages = new JsonArray();

        //Definindo a personalidade da AI
        String personalidade = dotenv.get("PERSONALIDADE");
        JsonObject systemMensagem = new JsonObject();
        systemMensagem.addProperty("role", "system");
        systemMensagem.addProperty("content", personalidade);


        // Cria a mensagem do usuário
        JsonObject usuarioMensagem = new JsonObject();
        usuarioMensagem.addProperty("role", "user"); // Quem está falando (user)
        usuarioMensagem.addProperty("content", pergunta); // A pergunta em si

        // Adiciona as mensagens ao array de mensagens
        messages.add(systemMensagem);
        messages.add(usuarioMensagem);
        body.add("messages", messages); // Adiciona ao corpo final da requisição

        // Constrói a requisição HTTP POST
        Request request = new Request.Builder()
                .url(API_URL)
                .addHeader("Authorization", "Bearer " + API_KEY) // Autenticação
                .addHeader("Content-Type", "application/json")
                .post(RequestBody.create(gson.toJson(body), MediaType.parse("application/json"))) // Converte o JSON para string e envia
                .build();

        // Envia a requisição e trata a resposta
        try (Response response = client.newCall(request).execute()) {

            // Verifica se a resposta foi bem-sucedida
            if (!response.isSuccessful()) {
                return "Erro na requisição: " + response.code();
            }

            // Lê o corpo da resposta da API
            String responseBody = response.body().string();
            System.out.println("Resposta da API: " + responseBody);

            // Interpreta a string como JSON
            JsonObject json = JsonParser.parseString(responseBody).getAsJsonObject();

            // Extrai a resposta da IA no campo "choices[0].message.content"
            return json.getAsJsonArray("choices")
                    .get(0).getAsJsonObject()
                    .getAsJsonObject("message")
                    .get("content").getAsString();

        } catch (IOException e) {

            e.printStackTrace();
            return "Erro ao se comunicar com a IA.";
        }
    }
}