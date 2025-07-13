package org.example.services;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.*;
import org.slf4j.LoggerFactory;

import org.slf4j.Logger;

import java.io.IOException;

public class JokeService {

    private static final Logger logger = LoggerFactory.getLogger(JokeService.class);
    private static final Dotenv dotenv = Dotenv.load();

    private static final String JOKE_URL = dotenv.get("JOKE_API_URL");

    private final OkHttpClient client = new OkHttpClient();
    private final Gson gson = new Gson();

    public String obterPiada() {

        Request request = new Request.Builder().url(JOKE_URL)
                .addHeader("Content-Type", "application/json").build();

        try {

            Response response = client.newCall(request).execute();

            if (!response.isSuccessful()) {
                return "Erro ao obter Piada <:astra_triste:1390823447227142204>";
            }

            String responseBody = response.body().string();

            JsonObject json = JsonParser.parseString(responseBody).getAsJsonObject();

            if (json.has("error") && json.get("error").getAsString().equals("false") && json.has("type")) {
                String tipo = json.get("type").getAsString();

                if (tipo.equals("twopart") && json.has("setup") && json.has("delivery")) {
                    String parte1 = json.get("setup").getAsString();
                    String parte2 = json.get("delivery").getAsString();

                    return parte1 + " " + "\n\n" + parte2 + " <:astra_rindo:1393788989202235486>";
                } else if (tipo.equals("single") && json.has("joke")) {
                    return json.get("joke").getAsString() + " <:astra_rindo:1393788989202235486>";
                }
            }


        } catch (IOException e) {
            logger.error("[ ERRO ] JOKE API: ", e.getMessage());
        }

        return "Parece que não pude contar a Piada <:astra_triste:1390823447227142204>";
    }
}