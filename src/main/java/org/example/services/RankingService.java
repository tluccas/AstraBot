package org.example.services;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.example.models.entities.Ranking;

import java.awt.*;
import java.util.List;

public class RankingService {

    public EmbedBuilder getRankingEmbedPrefix(List<Ranking> topRanking, MessageReceivedEvent event) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("<a:rank:1389632040294547456> Ranking - " + event.getGuild().getName());
        embed.setColor(Color.CYAN);
        embed.setFooter("Ranking • Astra Bot");

        StringBuilder desc = new StringBuilder();
        String top1Avatar = null;

        int posicao = 1;
        for (Ranking r : topRanking) {
            Member membro = event.getGuild().getMemberById(r.getUser_id());

            String mencao = (membro != null) ? membro.getAsMention() : "<@" + r.getUser_id() + ">";
            int pontos = r.getUser_pontos();

            String emoji = switch (posicao) {
                case 1 -> "<a:trofeu:1389631770143625408> ";
                case 2 -> "🥈 ";
                case 3 -> "🥉 ";
                default -> "🏅 ";
            };

            desc.append(emoji).append("**").append(posicao).append("º** - ")
                    .append(mencao).append(" (`").append(pontos).append(" pontos`)\n").append("\n");



            if (posicao == 1 && membro != null) {
                top1Avatar = membro.getUser().getEffectiveAvatarUrl();
            }

            posicao++;
        }

        embed.setDescription(desc.toString());
        // Coloca o avatar do top 1 como thumbnail
        if (top1Avatar != null) {
            embed.setThumbnail(top1Avatar);
        }
        embed.setImage(event.getGuild().getBannerUrl());

        return embed;
    }

    public EmbedBuilder getRankingEmbedSlash(List<Ranking> topRanking, SlashCommandInteractionEvent event) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("<a:rank:1389632040294547456> Ranking - " + event.getGuild().getName());
        embed.setColor(Color.CYAN);
        embed.setFooter("Ranking • Astra Bot");

        StringBuilder desc = new StringBuilder();
        String top1Avatar = null;

        int posicao = 1;
        for (Ranking r : topRanking) {
            Member membro = event.getGuild().getMemberById(r.getUser_id());

            String mencao = (membro != null) ? membro.getAsMention() : "<@" + r.getUser_id() + ">";
            int pontos = r.getUser_pontos();

            String emoji = switch (posicao) {
                case 1 -> "<a:trofeu:1389631770143625408> ";
                case 2 -> "🥈 ";
                case 3 -> "🥉 ";
                default -> "🏅 ";
            };

            desc.append(emoji).append("**").append(posicao).append("º** - ")
                    .append(mencao).append(" (`").append(pontos).append(" pontos`)\n").append("\n");



            if (posicao == 1 && membro != null) {
                top1Avatar = membro.getUser().getEffectiveAvatarUrl();
            }

            posicao++;
        }

        embed.setDescription(desc.toString());
        // Coloca o avatar do top 1 como thumbnail
        if (top1Avatar != null) {
            embed.setThumbnail(top1Avatar);
        }

        embed.setImage(event.getGuild().getBannerUrl());

        return embed;
    }
}
