package org.example.views;

import org.example.controllers.ComandoHandler;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.example.services.api.StatusApi;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class BotListener extends ListenerAdapter {

    private final ComandoHandler comandoHandler = new ComandoHandler();
    private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    private static final StatusApi statusApi = new StatusApi();

    public BotListener() {
        executor.scheduleAtFixedRate(statusApi.sendStatus(), 0, 30, TimeUnit.SECONDS);
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) { // Na mensagem recebida
        if (event.getAuthor().isBot()) return;

        String msg = event.getMessage().getContentRaw();
        if (msg.startsWith("*")) {
            comandoHandler.handlePrefixo(event); //Chama o controller para lidar com o conteúdo da mensagem
        }
    }

    public ScheduledExecutorService getExecutor() {
        return executor;
    }

}