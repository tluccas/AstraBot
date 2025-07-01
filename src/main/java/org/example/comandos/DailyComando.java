package org.example.comandos;

import org.example.dao.RankingDAO;
import org.example.models.entities.Ranking;
import org.example.models.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.example.dao.UserDAO;
import org.example.models.Comando;

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
        // Busca o usuário no banco
        User user = userDAO.obterUser(userID);

        // Se não existir, cria um novo com daily = false
        if (user == null) {

            String userNome = event.getAuthor().getName();
            user = new User(userID, guildID, userNome, false, null);
        }

        // Verifica se já resgatou hoje
        LocalDate hoje = LocalDate.now();
        LocalDate ultimoResgate = user.getUltimo_resgate();
        if (ultimoResgate != null && hoje.isEqual(user.getUltimo_resgate())){
            event.getChannel().sendMessage("<a:erro:1389606208519802880>"+ event.getAuthor().getAsMention() +
                    " Você já realizou seu resgate diário!").queue();
            return;
        }

        //Atualiza o usuario
        user.setUltimo_resgate(hoje);
        user.setDaily(true);
        userDAO.salvarUser(user);

        //Atualiza o ranking
        Ranking ranking = rankingDAO.obterRanking(userID, guildID);

        if (ranking == null){
            ranking = new Ranking(guildID, userID, 100);
        } else {
            ranking.setUser_pontos(ranking.getUser_pontos() + 100);
        }
        rankingDAO.salvarRanking(ranking);
        event.getChannel().sendMessage("<a:money:1389606252660658367> "
                 +event.getAuthor().getAsMention() + " resgatou **100** pontos!").queue();

    }

    @Override
    public void executarSlash(SlashCommandInteractionEvent event) {
        long userID = event.getUser().getIdLong();
        long guildID = event.getGuild().getIdLong();
        User user = userDAO.obterUser(userID);

        if (user == null) {

            String userNome = event.getUser().getName();
            user = new User(userID, guildID, userNome, false, null);
        }

        LocalDate hoje = LocalDate.now();
        if (hoje.isEqual(user.getUltimo_resgate())){
            event.reply("<a:erro:1389606208519802880> "+ event.getUser().getAsMention() +
                    " Você já realizou seu resgate diário!").queue();
            return;
        }

        //Atualiza o usuario
        user.setUltimo_resgate(hoje);
        user.setDaily(true);
        userDAO.salvarUser(user);

        //Atualiza o ranking
        Ranking ranking = rankingDAO.obterRanking(userID, guildID);

        if (ranking == null){
            ranking = new Ranking(guildID, userID, 100);
        } else {
            ranking.setUser_pontos(ranking.getUser_pontos() + 100);
        }
        rankingDAO.salvarRanking(ranking);
        event.reply("<a:money:1389606252660658367> "
                +event.getUser().getAsMention() + " resgatou **100** pontos!").queue();

    }
}
