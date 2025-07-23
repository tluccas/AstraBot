package org.example.comandos;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.example.models.Comando;
import org.example.util.exceptions.NoGuildException;

public class CountComando implements Comando {


    @Override
    public String getNomeComando() {
        return "servidores";
    }

    @Override
    public String getDescricao() {
        return "Mostra em quantos servidores o bot está :)";
    }

    @Override
    public void executar(MessageReceivedEvent event, String[] args) {

        int contador = event.getJDA().getGuilds().size();
        String resposta = "Olá " + event.getAuthor().getAsMention() + " o bot está em " + contador+ " servidores";

        event.getChannel().sendMessage(resposta).queue();
    }

    @Override
    public void executarSlash(SlashCommandInteractionEvent event) {
        try{
            if(event.getGuild() == null){
                throw new NoGuildException(event.getUser().getAsMention());
            }
            int contador = event.getJDA().getGuilds().size();
            String resposta = "Olá " + event.getUser().getAsMention() + " o bot está em " + contador + " servidores";

            event.getChannel().sendMessage(resposta).queue();

        }catch (NoGuildException e){
            event.reply("OPS, envie uma quantidade válida (quantidade min = 2 e máx = 100) <a:cat:1388352178510630952>").queue();
        }
    }

}
