package org.example.comandos.testes;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.example.dao.GuildJoinMessageDAO;
import org.example.models.Comando;
import org.example.models.entities.GuildJoinMessage;

import java.awt.*;

public class TesteBoasVindasComando implements Comando {
    private final GuildJoinMessageDAO dao;

    public TesteBoasVindasComando(GuildJoinMessageDAO dao) {
        this.dao = dao;
    }

    @Override
    public String getNomeComando() {
        return "testboasvindas";
    }

    @Override
    public String getDescricao() {
        return "Envia a mensagem de boas-vindas configurada (para testes)";
    }

    @Override
    public void executar(MessageReceivedEvent event, String[] args) {
        long guildId = event.getGuild().getIdLong();
        GuildJoinMessage config = dao.obterPorGuildId(guildId);

        if (config == null || config.getMensagem() == null || config.getCanalId() == 0L) {
            event.getChannel().sendMessage("❌ Nenhuma mensagem de boas-vindas configurada.").queue();
            return;
        }

        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Bem-vindo(a) ao " + event.getGuild().getName() + " " + event.getAuthor().getName() + "!");
        eb.setDescription(config.getMensagem().replace("{user}", event.getAuthor().getAsMention()));
        eb.setColor(Color.YELLOW);
        eb.setThumbnail(event.getAuthor().getEffectiveAvatarUrl());
        //Insere a imagem se existir
        if (config.getImagem_url() != null && !config.getImagem_url().isEmpty()) {
            eb.setImage(config.getImagem_url());
        }

        TextChannel canal = event.getGuild().getTextChannelById(config.getCanalId());
        if (canal != null) {
            canal.sendMessageEmbeds(eb.build()).queue();
            event.getChannel().sendMessage("✅ Mensagem de boas-vindas enviada no canal configurado.").queue();
        } else {
            event.getChannel().sendMessage("❌ Canal de boas-vindas não encontrado!").queue();
        }
    }
}
