package org.example.comandos;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.example.models.Comando;
import org.example.services.AiService;

public class ComandoAI implements Comando {
    private final AiService aiService = new AiService();

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

            String pergunta = String.join(" ", args);
            event.getChannel().sendTyping().queue(); // o bot fica digitando
            String resposta = aiService.obterResposta(pergunta);
            event.getChannel().sendMessage(resposta).queue();
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

        // responde
        String resposta = aiService.obterResposta(pergunta);

        // Envia a resposta
        event.getHook().sendMessage(resposta).queue();
    }
}
