package org.example.views;

import net.dv8tion.jda.api.JDA;
import org.example.controllers.ComandoHandler;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class SlashComandoListener extends ListenerAdapter {

    private final ComandoHandler comandoHandler = new ComandoHandler();

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        // Ignora comandos vindos de bots (opcional)
        if (event.getUser().isBot()) return;

        String nomeComando = event.getName().toLowerCase();

        // Chama seu handler, que precisa ter método para lidar com SlashCommandInteractionEvent
        comandoHandler.handleSlash(event, nomeComando);
    }
}
