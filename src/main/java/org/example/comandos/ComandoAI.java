package org.example.comandos;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.example.dao.MemoriaIADAO;
import org.example.models.Comando;
import org.example.models.entities.MemoriaIA;
import org.example.services.AiService;

public class ComandoAI implements Comando {
    private final AiService aiService = new AiService();
    private final MemoriaIADAO memoriaDAO = new MemoriaIADAO();

    @Override
    public String getNomeComando() {
        return "astra";
    }

    @Override
    public String getDescricao() {
        return "Vai te responder utilizando IA <a:robo:1387819870321905684>";
    }

    @Override
    public void executar(MessageReceivedEvent event, String[] args) {
        if (args.length == 0 || args[0].isBlank()) {
            event.getChannel().sendMessage("Você precisa perguntar ou falar algo,  exemplo: `*astra qual a melhor " +
                    "equipe da Formula 1 `(Ferrari) <a:idontcare:1387821007955296256>").queue();
            return;
        }

        long userID = event.getAuthor().getIdLong();

        MemoriaIA memoria = memoriaDAO.obterMemoria(userID);
        String memoriaAnterior = (memoria != null) ? memoria.getConteudo() : "";

        String pergunta = String.join(" ", args);
        event.getChannel().sendTyping().queue(); // o bot fica digitando
        String resposta = aiService.obterResposta(pergunta, memoriaAnterior);
        event.getChannel().sendMessage(resposta).queue();

        int tokensDaResposta = aiService.getTokens();

        if (memoria == null) {
            memoria = new MemoriaIA(userID, pergunta, tokensDaResposta);
        } else {

            if (memoria.getTokens() + tokensDaResposta > 150.000) {
                memoriaDAO.limparMemoria(userID);    // zera o contador
            }

            // Concatena o novo conteúdo à memória e acumula tokens
            String novoConteudo = memoria.getConteudo() + "\nUsuário: " + pergunta + "\nIA: " + resposta;
            memoria.setConteudo(novoConteudo);
            memoria.setTokens(memoria.getTokens() + tokensDaResposta);
        }


        memoriaDAO.salvarMemoria(memoria);

    }


    //Metodo executar adaptado para slash command
    @Override
    public void executarSlash(SlashCommandInteractionEvent event) {
        // recebe a mensagem
        String pergunta = event.getOption("olá") != null ? event.getOption("olá").getAsString() : "";

        if (pergunta.isBlank()) {
            event.reply("Você precisa perguntar ou falar algo, exemplo: `/astra pergunta: qual a melhor equipe da Formula 1` (Ferrari) <a:idontcare:1387821007955296256>")
                    .setEphemeral(true)
                    .queue();
            return;
        }

        event.deferReply().queue(); // evita timeout (reserva o tempo pra responder)

        long userID = event.getUser().getIdLong();

        MemoriaIA memoria = memoriaDAO.obterMemoria(userID);
        String memoriaAnterior = (memoria != null) ? memoria.getConteudo() : "";

        // responde
        String resposta = aiService.obterResposta(pergunta, memoriaAnterior);


        int tokensDaResposta = aiService.getTokens();

        if (memoria == null) {
            memoria = new MemoriaIA(userID, pergunta, tokensDaResposta);
        } else {

            if(memoria.getTokens() + tokensDaResposta > 150.000) {
                memoriaDAO.limparMemoria(userID);     // zera o contador
            }

            // Concatena o novo conteúdo à memória e acumula tokens
            String novoConteudo = memoria.getConteudo() + "\nUsuário: " + pergunta + "\nIA: " + resposta;
            memoria.setConteudo(novoConteudo);
            memoria.setTokens(memoria.getTokens() + tokensDaResposta);
        }


        memoriaDAO.salvarMemoria(memoria);

        // Envia a resposta
        event.getHook().sendMessage(resposta).queue();
    }
}
