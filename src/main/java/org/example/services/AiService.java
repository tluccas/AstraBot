package org.example.services;

import com.google.gson.*;
import okhttp3.*;
import java.io.IOException;
import java.net.SocketTimeoutException;

public class AiService {


    private static final String API_URL = System.getenv("OPENROUTER_API_URL"); // API que irá ser usada (DeepSeek R1)
    private static final String MODEL = "deepseek/deepseek-r1-0528:free";
    private static final String API_KEY = System.getenv("OPENROUTER_API_KEY"); // carregando a API do openrouter


    private final OkHttpClient client = new OkHttpClient();
    private final Gson gson = new Gson();

    private int totalTokens = 0;

    public String obterResposta(String pergunta, String memoriaAnterior) {
        JsonObject body = new JsonObject();
        body.addProperty("model", MODEL); // Define o modelo da IA a ser usado

        JsonArray messages = new JsonArray();

        // Definindo a personalidade da IA
        String personalidade = System.getenv("PERSONALIDADE");
        JsonObject systemMensagem = new JsonObject();
        systemMensagem.addProperty("role", "system");
        systemMensagem.addProperty("content", personalidade);
        messages.add(systemMensagem); // Adiciona mensagem de sistema

        // Adiciona mensagens da memória anterior do usuário (caso exista)
        if (memoriaAnterior != null && !memoriaAnterior.isBlank()) {
            String memoriaLimitada = limitarMemoria(memoriaAnterior, 4000); // +/- 1000 tokens
            String[] linhas = memoriaLimitada.split("\n");

            for (String linha : linhas) {
                if (linha.startsWith("Usuário: ")) {
                    JsonObject userMsg = new JsonObject();
                    userMsg.addProperty("role", "user");
                    userMsg.addProperty("content", linha.replace("Usuário: ", ""));
                    messages.add(userMsg);
                } else if (linha.startsWith("IA: ")) {
                    JsonObject aiMsg = new JsonObject();
                    aiMsg.addProperty("role", "assistant");
                    aiMsg.addProperty("content", linha.replace("IA: ", ""));
                    messages.add(aiMsg);
                }
            }
        }

        // Mensagem atual do usuário
        JsonObject usuarioMensagem = new JsonObject();
        usuarioMensagem.addProperty("role", "user");
        usuarioMensagem.addProperty("content", pergunta);
        messages.add(usuarioMensagem);

        body.add("messages", messages);
    
        Request request = new Request.Builder()
                .url(API_URL)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .addHeader("Content-Type", "application/json")
                .post(RequestBody.create(gson.toJson(body), MediaType.parse("application/json")))
                .build();

        try (Response response = client.newCall(request).execute()) {

            if (!response.isSuccessful()) {
                System.out.println("[ERRO DA IA] (Código: " + response.code() + ")");
                return "Erro da IA (código " + response.code() + " )";
            }

            String responseBody = response.body().string();
            System.out.println("Resposta da API: " + responseBody); // Log de debug

            JsonObject json = JsonParser.parseString(responseBody).getAsJsonObject();

            // NOVO: Armazena os tokens usados na resposta (se houver)
            if (json.has("usage") && json.get("usage").isJsonObject()) {
                this.totalTokens = json.getAsJsonObject("usage").get("total_tokens").getAsInt();
            }

            JsonArray choices = json.getAsJsonArray("choices");
            if (choices != null && choices.size() > 0) {
                JsonObject choice = choices.get(0).getAsJsonObject();

                // ✅ NOVO: Verificação segura do campo "message"
                if (choice.has("message") && choice.get("message").isJsonObject()) {
                    JsonObject messageObj = choice.getAsJsonObject("message");
                    if (messageObj.has("content")) {
                        return messageObj.get("content").getAsString();
                    }
                }

                // ✅ NOVO: Suporte alternativo para formato com "text" (ex: completions)
                if (choice.has("text") && choice.get("text").isJsonPrimitive()) {
                    return choice.get("text").getAsString();
                }
            }

            // NOVO: Log de erro de estrutura inesperada
            System.out.println("⚠️ Estrutura inesperada na resposta da IA: " + responseBody);
            return "A IA não retornou uma resposta válida.";

        } catch (SocketTimeoutException e) {
            System.out.println("⏱️ Timeout na requisição para IA.");
            return "⏳ A IA demorou demais para responder. Tente novamente em instantes.";
        } catch (IOException e) {
            e.printStackTrace();
            return "❌ Erro ao tentar se comunicar com a IA.";
        } catch (Exception e) {
            e.printStackTrace();
            return "❌ Ocorreu um erro inesperado ao processar a resposta da IA.";
        }
    }

    public int getTokens() {
            return totalTokens;
    }

    private String limitarMemoria(String memoriaAnterior, int limiteCaracteres) {
        StringBuilder memoriaLimitada = new StringBuilder();
        int total = 0;

        String[] linhas = memoriaAnterior.split("\n");
        for (int i = linhas.length - 1; i >= 0; i--) {
            String linha = linhas[i];
            if ((total + linha.length()) > limiteCaracteres) break;

            memoriaLimitada.insert(0, linha + "\n");
            total += linha.length();
        }

        return memoriaLimitada.toString();
    }
}