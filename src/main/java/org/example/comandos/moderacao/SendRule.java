package org.example.comandos.moderacao;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.example.models.Comando;
import java.awt.Color;

public class SendRule implements Comando {
    @Override
    public String getNomeComando() {
        return "msg";
    }

    @Override
    public String getDescricao() {
        return "";
    }

    @Override
    public void executar(MessageReceivedEvent event, String[] args) {

        EmbedBuilder emb = new EmbedBuilder();
        emb.setTitle(" **SITE DA ASTRA**");
        emb.setDescription("\n**ACESSE: ** https://tluccas.github.io/AstraSite");
        emb.setImage("https://cdn.discordapp.com/attachments/1395994039324508318/1396180837061492868/SITE.PNG?ex=687d263b&is=687bd4bb&hm=da2b9925b73eb90657b5002517a2dce885dbe46cb63a656645a914e94b8aa6ba&");
        emb.setFooter("Site • Astra");
        Color color = new Color(59, 226, 220);
        emb.setColor(color);
        event.getChannel().sendMessageEmbeds(emb.build()).queue();
    }

    @Override
    public void executarSlash(SlashCommandInteractionEvent event) {
        Comando.super.executarSlash(event);
    }
}
