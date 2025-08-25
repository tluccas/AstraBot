package org.example.comandos.diversos;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.example.models.Comando;
import org.example.services.api.MemeAbyssService;
import java.awt.Color;

public class MemeComando implements Comando {

    private final MemeAbyssService memeAbyssService = new MemeAbyssService();
    @Override
    public String getNomeComando() {
        return "meme";
    }

    @Override
    public String getDescricao() {
        return "Envia um meme de qualidade duvidosa";
    }

    @Override
    public void executar(MessageReceivedEvent event, String[] args) {

        String meme = memeAbyssService.obterMemeRandom();

        String[] partes = meme.split("\n");

        String titulo = partes[0];
        String imgUrl = partes[1];

        EmbedBuilder eb = new EmbedBuilder();
        Color color = new Color(135,206,250);
        eb.setColor(color);
        eb.setImage(imgUrl);
        event.getChannel().sendMessageEmbeds(eb.build()).queue();
    }

    @Override
    public void executarSlash(SlashCommandInteractionEvent event) {
        event.deferReply().queue();
        String meme = memeAbyssService.obterMemeRandom();

        String[] partes = meme.split("\n");

        String titulo = partes[0];
        String imgUrl = partes[1];

        EmbedBuilder eb = new EmbedBuilder();
        Color color = new Color(135,206,250);
        eb.setColor(color);
        eb.setTitle(titulo);
        eb.setImage(imgUrl);
        eb.setFooter("PostAbyss API • Alves Dev");
        event.replyEmbeds(eb.build()).queue();
    }
}
