package org.example.models;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public interface ComandoSlash {

    String getNome();
    String getDescricao();

    void executarSlash(SlashCommandInteractionEvent event);
}
