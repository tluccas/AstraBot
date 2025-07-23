package org.example.views;

import org.example.controllers.ComandoHandler;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class BotListener extends ListenerAdapter {


    private final ComandoHandler comandoHandler = new ComandoHandler();

    @Override
    public void onMessageReceived(MessageReceivedEvent event) { // Na mensagem recebida
        if (event.getAuthor().isBot()) return;

        String msg = event.getMessage().getContentRaw();
        if (msg.startsWith("*")) {
            comandoHandler.handlePrefixo(event); //Chama o controller para lidar com o conteúdo da mensagem
        }
    }
}