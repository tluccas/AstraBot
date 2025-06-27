package org.example.views;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.example.dao.GuildJoinMessageDAO;

import org.example.models.entities.GuildJoinMessage;

import java.awt.*;

public class GuildMemberJoinListener extends ListenerAdapter {

    private final GuildJoinMessageDAO joinMessageDAO = new GuildJoinMessageDAO();

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        long guildId = event.getGuild().getIdLong();

        // Busca mensagem e canal personalizados no banco
        GuildJoinMessage config = joinMessageDAO.obterPorGuildId(guildId);

        if (config == null || config.getMensagem() == null || config.getCanalId() == 0L) {
            return; // Nenhuma mensagem ou canal configurado
        }
        // Substitui {user} pela menção do usuário que entrou
        String mensagemFormatada = config.getMensagem()
                .replace("{user}", event.getUser().getAsMention());


        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Bem-vindo(a) ao " + event.getGuild().getName() + " " + event.getUser().getAsMention() + "! <a:ola:1388193223520030850>");
        eb.setDescription(mensagemFormatada);
        eb.setColor(Color.YELLOW);
        eb.setThumbnail(event.getUser().getEffectiveAvatarUrl());
        // Verifica se o canal ainda existe
        TextChannel canal = event.getGuild().getTextChannelById(config.getCanalId());
        //Insere a imagem se existir
        if (config.getImagem_url() != null && !config.getImagem_url().isEmpty()) {
            eb.setImage(config.getImagem_url());
        }
        if (canal == null) {
            System.out.println("❌ Canal de boas-vindas não encontrado ou Astra perdeu acesso. ID: " + config.getCanalId());
            return;
        }

        // Envia a embed no canal configurado
        canal.sendMessageEmbeds(eb.build()).queue();
    }
}