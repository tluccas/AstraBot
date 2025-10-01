package org.example.services.api;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDateTime;

public class StatusApi {

    private final OkHttpClient client = new OkHttpClient();
    private static final Logger logger = LoggerFactory.getLogger(StatusApi.class);

    public Runnable sendStatus() {
        return new Runnable() {
            @Override
            public void run() {
                Request request = new Request.Builder()
                        .url("http://localhost:8080/astra/up-status").post(RequestBody.create(new byte[0]))
                        .build();

                try (Response response = client.newCall(request).execute()) {
                    logger.info("Status atualizado: " + response.code() + " - " + LocalDateTime.now());
                } catch (IOException e) {
                    logger.error("Erro ao enviar status: " + e.getMessage());
                }
            }
        };
    }
}

