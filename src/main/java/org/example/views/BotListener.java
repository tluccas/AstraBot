package org.example.views;

import net.dv8tion.jda.api.JDA;
import org.example.controllers.ComandoHandler;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class BotListener extends ListenerAdapter {

    private static JDA builder;

    public BotListener(JDA builder) {
        this.builder = builder;
    }

    private final ComandoHandler comandoHandler = new ComandoHandler(builder);

    @Override
    public void onMessageReceived(MessageReceivedEvent event) { // Na mensagem recebida
        if (event.getAuthor().isBot()) return;

        String msg = event.getMessage().getContentRaw();
        if (msg.startsWith("*")) {
            comandoHandler.handlePrefixo(event); //Chama o controller para lidar com o conteúdo da mensagem
        }
    }
}