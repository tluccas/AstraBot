package org.example.comandos;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.commands.Command;
import org.example.controllers.ComandoHandler;
import org.example.models.Comando;
import org.example.models.ComandoSlash;

import java.awt.*;

public class ComandoHelp implements Comando {
    private final ComandoHandler handler;

    public ComandoHelp(ComandoHandler handler) {
        this.handler = handler;
    }
    @Override
    public String getNomeComando() {
        return "help";
    }

    @Override
    public String getDescricao() {
        return "Comando de ajuda";
    }

    @Override
    public void executar(MessageReceivedEvent event, String[] args) {
            EmbedBuilder eb = new EmbedBuilder();
            StringBuilder builder = new StringBuilder();
            eb.setTitle("<a:helper:1387654876414214215> Comandos da Astra");
            for (Comando cmd : handler.getComandos().values()) {
            builder.append("`*")
                    .append(cmd.getNomeComando())
                    .append("` - ")
                    .append(cmd.getDescricao())
                    .append("\n\n");
        };

            eb.addField("\n", builder.toString(), false);
            eb.setThumbnail("https://raw.githubusercontent.com/gist/dumbmoron/ea9b6264e6b6183fd590e322d1afab51/raw/bc064a9116403eab89e5b8200b1aa0890419ec0e/cat.gif");
            eb.setColor(Color.RED);
            event.getChannel().sendMessageEmbeds(eb.build()).queue();
    }

    @Override
    //Comando Slash (primeira implementação de slash)
    public void executarSlash(SlashCommandInteractionEvent event) {
        EmbedBuilder eb = new EmbedBuilder();
        StringBuilder builder = new StringBuilder();
        eb.setTitle("<a:helper:1387654876414214215> Comandos da Astra");
        for (Comando cmd : handler.getComandos().values()) {
            builder.append("`*")
                    .append(cmd.getNomeComando())
                    .append("` - ")
                    .append(cmd.getDescricao())
                    .append("\n\n");
        }
        eb.addField("\n", builder.toString(), false);
        eb.setThumbnail("https://raw.githubusercontent.com/gist/dumbmoron/ea9b6264e6b6183fd590e322d1afab51/raw/bc064a9116403eab89e5b8200b1aa0890419ec0e/cat.gif");
        eb.setColor(Color.RED);

        event.replyEmbeds(eb.build()).queue();
    }
}
