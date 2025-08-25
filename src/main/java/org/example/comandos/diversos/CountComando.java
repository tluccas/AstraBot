package org.example.comandos.diversos;

import net.dv8tion.jda.api.EmbedBuilder;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.example.models.Comando;
import org.example.util.exceptions.NoGuildException;

import java.awt.*;

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
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Astra está trabalhando em **" + contador + "** servidores");
        eb.setImage("https://i.pinimg.com/originals/c8/4c/3a/c84c3a379701f76bc6742bd807c026ef.gif");
        eb.setColor(Color.RED);

        event.getChannel().sendMessageEmbeds(eb.build()).queue();
    }

    @Override
    public void executarSlash(SlashCommandInteractionEvent event) {
        try{
            if(event.getGuild() == null){
                throw new NoGuildException(event.getUser().getAsMention());
            }
            int contador = event.getJDA().getGuilds().size();
            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle("Astra está trabalhando em **" + contador + "** servidores");
            eb.setImage("https://i.pinimg.com/originals/c8/4c/3a/c84c3a379701f76bc6742bd807c026ef.gif");
            eb.setColor(Color.RED);


            event.replyEmbeds(eb.build()).queue();

        }catch (NoGuildException e){
            event.reply("OPS, esse comando só pode ser usado em um servidor <a:cat:1388352178510630952>").queue();
        }
    }

}
