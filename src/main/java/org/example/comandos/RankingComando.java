package org.example.comandos;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.example.dao.RankingDAO;
import org.example.models.Comando;
import org.example.models.entities.Ranking;
import org.example.services.RankingService;
import org.example.util.exceptions.NoGuildException;

import java.util.List;

public class RankingComando implements Comando {

    private final RankingDAO rankingDAO;

    public RankingComando(RankingDAO rankingDAO) {
        this.rankingDAO = rankingDAO;
    }

    @Override
    public String getNomeComando() {
        return "rank";
    }

    @Override
    public String getDescricao() {
        return "Exibe o ranking do servidor";
    }

    @Override
    public void executar(MessageReceivedEvent event, String[] args) {
        long guildId = event.getGuild().getIdLong();

        // Busca os top 10 usuários
        List<Ranking> topRanking = rankingDAO.listarRankingPorGuild(guildId, 10);

        if (topRanking.isEmpty()) {
            event.getChannel().sendMessage("<a:pain:1389606479270772918> Ainda não há usuários no ranking.").queue();
            return;
        }

        RankingService rankingService = new RankingService();
        EmbedBuilder embed = rankingService.getRankingEmbedPrefix(topRanking, event);

        event.getChannel().sendMessageEmbeds(embed.build()).queue();

    }

    @Override
    public void executarSlash(SlashCommandInteractionEvent event) {
        try{
            // O comando só pode ser usado em servidor
        if (event.getGuild() == null) {
            throw new NoGuildException(event.getUser().getAsMention());
        }

            long guildId = event.getGuild().getIdLong();

            // Busca os top 10 usuários
            List<Ranking> topRanking = rankingDAO.listarRankingPorGuild(guildId, 10);

            if (topRanking.isEmpty()) {
                event.reply("<a:pain:1389606479270772918> Ainda não há usuários no ranking.").queue();
                return;
            }

            RankingService rankingService = new RankingService();
            EmbedBuilder embed = rankingService.getRankingEmbedSlash(topRanking, event);

            event.replyEmbeds(embed.build()).queue();
        } catch (NoGuildException e) {
            event.reply(e.getMessage()).queue();
        }
    }
}
