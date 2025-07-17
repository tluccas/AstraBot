package org.example.comandos.diversos;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.example.models.Comando;
import org.example.services.api.JokeService;


public class PiadaComando implements Comando {

    private final JokeService jokeService = new JokeService();

    @Override
    public String getNomeComando() {
        return "piada";
    }

    @Override
    public String getDescricao() {
        return "Astra vai te contar uma piada";
    }

    @Override
    public void executar(MessageReceivedEvent event, String[] args) {
            String usuario = event.getAuthor().getAsMention();
            String piada = jokeService.obterPiada();

            event.getChannel().sendMessage(usuario + " " + piada).queue();
    }

    @Override
    public void executarSlash(SlashCommandInteractionEvent event) {
        String usuario = event.getUser().getAsMention();
        String piada = jokeService.obterPiada();

        event.reply(usuario + " " + piada).setEphemeral(false).queue();
    }
}
