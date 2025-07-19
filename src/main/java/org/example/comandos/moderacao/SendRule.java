package org.example.comandos.moderacao;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.example.models.Comando;
import java.awt.Color;

public class SendRule implements Comando {
    @Override
    public String getNomeComando() {
        return "rule";
    }

    @Override
    public String getDescricao() {
        return "";
    }

    @Override
    public void executar(MessageReceivedEvent event, String[] args) {

        EmbedBuilder emb = new EmbedBuilder();
        emb.setTitle(" **REGRAS DA COMUNIDADE**");
        emb.setDescription("\n**1. Proibido conteúdo impróprio **" +
                "Isso inclui textos, áudios, links ou qualquer tipo de mídia com conteúdo sexual, violento ou perturbador.\n" +
                "\n" +
                "**2. Proibido qualquer forma de discriminação**" +
                "Não toleramos racismo, homofobia, xenofobia, sexismo ou qualquer outra forma de preconceito.\n" +
                "\n" +
                "**3. Proibido discussões ofensivas ou provocativas**" +
                "Evite brigas, ofensas pessoais e tópicos polêmicos que possam gerar conflito desnecessário.\n" +
                "\n" +
                "**4. Proibido spam**" +
                "Não envie mensagens repetitivas, links aleatórios, flood ou divulgações sem autorização da moderação.\n" +
                "\n" +
                "**5. Proibido envio de imagens com conteúdo impróprio**" +
                "Memes ou imagens ofensivas, chocantes, explícitas ou inadequadas não serão permitidas.\n" +
                "\n" +
                "**6. Respeite todos os membros**" +
                "Trate os demais com educação e empatia. Discordâncias são normais, mas sempre com respeito.\n" +
                "\n" +
                "**7. Sem pirataria**" +
                "Não compartilhe, solicite ou incentive o uso de software, jogos, filmes ou qualquer outro conteúdo de forma ilegal.\n" +
                "\n");
        emb.setFooter("Regras • Astra");
        Color color = new Color(59, 226, 220);
        emb.setColor(color);
        event.getChannel().sendMessageEmbeds(emb.build()).queue();
    }

    @Override
    public void executarSlash(SlashCommandInteractionEvent event) {
        Comando.super.executarSlash(event);
    }
}
