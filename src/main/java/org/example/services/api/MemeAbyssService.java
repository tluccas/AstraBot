package org.example.services.api;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class MemeAbyssService {

    private static final Logger logger = LoggerFactory.getLogger(MemeAbyssService.class);
    private static final Dotenv dotenv = Dotenv.load();

    private static final String MEMEABYSS_URL = dotenv.get("MEME_ABYSS_URL");

    private final OkHttpClient client = new OkHttpClient();

    public String obterMemeRandom() {
        Request request = new Request.Builder().url(MEMEABYSS_URL)
                .addHeader("Content-Type", "application/json").build();

        try {

           Response response = client.newCall(request).execute();

           if (!response.isSuccessful()) {
               return "Parece que houve um erro ao obter o meme <:astra_triste:1390823447227142204>";
           }

           String responseBody = response.body().string();

           JsonObject json = JsonParser.parseString(responseBody).getAsJsonObject();

           String titulo;
           String imgUrl;

           if(json.has("titulo") && !json.get("titulo").isJsonNull() && json.has("url") && !json.get("url").isJsonNull()) {
               titulo = json.get("titulo").getAsString();
               imgUrl = json.get("url").getAsString();

           }else if (json.has("url") && !json.get("url").isJsonNull()) {
               imgUrl = json.get("url").getAsString();
               titulo = " ";
           }else{
               return "Não foi possível carregar o meme <:astra_triste:1390823447227142204>";
           }
           System.out.println(titulo);
           return titulo + "\n" + imgUrl;


        }catch (IOException e){
            logger.error("[ ERRO ] MEME ABYSS API: ", e.getMessage());
            return null;
        }
    }
}