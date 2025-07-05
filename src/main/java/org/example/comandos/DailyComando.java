package org.example.comandos;

import org.example.dao.RankingDAO;
import org.example.models.entities.Ranking;
import org.example.models.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.example.dao.UserDAO;
import org.example.models.Comando;
import org.example.util.exceptions.NoGuildException;

import java.time.LocalDate;

public class DailyComando implements Comando {

    private final UserDAO userDAO;
    private final RankingDAO rankingDAO;

    public DailyComando(UserDAO userDAO, RankingDAO rankingDAO) {
        this.userDAO = userDAO;
        this.rankingDAO = rankingDAO;
    }

    @Override
    public String getNomeComando() {
        return "daily";
    }

    @Override
    public String getDescricao() {
        return "Resgate seus pontos diários";
    }

    @Override
    public void executar(MessageReceivedEvent event, String[] args) {

        long userID = event.getAuthor().getIdLong();
        long guildID = event.getGuild().getIdLong();

        String userNome = event.getAuthor().getName();
        User user = userDAO.obterUser(userID);
        if (user == null) {
            user = new User(userID, guildID, userNome);
            userDAO.salvarUser(user); // salva o user só se não existir
        }

        Ranking userRanking = rankingDAO.obterRanking(userID, guildID);
        LocalDate hoje = LocalDate.now();

        // se não existir ranking, cria um novo
        if (userRanking == null) {
            userRanking = new Ranking(guildID, userID, 0, false, null);
        }

        // Verifica se o usuário já fez o daily hoje
        if (hoje.equals(userRanking.getUltimo_resgate())) {
            event.getChannel().sendMessage("<a:erro:1389606208519802880> "
                    + event.getAuthor().getAsMention() + " Você já realizou seu resgate diário!").queue();
            return;
        }

        // Atualiza o ranking
        userRanking.setUser_pontos(userRanking.getUser_pontos() + 100);
        userRanking.setDaily(true);
        userRanking.setUltimo_resgate(hoje);
        rankingDAO.salvarRanking(userRanking);

        event.getChannel().sendMessage("<a:money:1389606252660658367> "
                + event.getAuthor().getAsMention() + " resgatou **100** pontos!").queue();
    }

    @Override
    public void executarSlash(SlashCommandInteractionEvent event) {
        try{

        if (event.getGuild() == null) {
            throw new NoGuildException(event.getUser().getAsMention());
        }
            long userID = event.getUser().getIdLong();
            long guildID = event.getGuild().getIdLong();

            // Obtém ou cria usuário
            User user = userDAO.obterUser(userID);
            if (user == null) {
                String userNome = event.getUser().getName();
                user = new User(userID, guildID, userNome);
                userDAO.salvarUser(user);
            }

            // Obtém ou cria ranking
            Ranking userRanking = rankingDAO.obterRanking(userID, guildID);
            if (userRanking == null) {
                userRanking = new Ranking(guildID, userID, 0, false, null);
            }

            // Verifica se já fez o daily hoje
            LocalDate hoje = LocalDate.now();
            if (hoje.equals(userRanking.getUltimo_resgate())) {
                event.reply("<a:erro:1389606208519802880> " + event.getUser().getAsMention()
                        + " Você já realizou seu resgate diário!").setEphemeral(true).queue();
                return;
            }

            // Atualiza ranking (pontos +100)
            userRanking.setUser_pontos(userRanking.getUser_pontos() + 100);
            userRanking.setDaily(true);
            userRanking.setUltimo_resgate(hoje);
            rankingDAO.salvarRanking(userRanking);

            event.reply("<a:money:1389606252660658367> "
                    + event.getUser().getAsMention() + " resgatou **100** pontos!").queue();
        } catch (NoGuildException e) {
            event.reply(e.getMessage()).queue();
        }
    }
}
