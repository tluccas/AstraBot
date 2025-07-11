package org.example.services.moderacao;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SpamAlertaService extends ListenerAdapter {

    private final Map<String, List<String>> mensagemCache = new HashMap<>();
    private final Map<String, Integer> avisos = new HashMap<>();
    private UserBanService userBanService;


    public SpamAlertaService() {
        userBanService = new UserBanService();

    }
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

    }

    public void processarSpam(int tolerancia, MessageReceivedEvent event) {

        if (event.getAuthor().isBot() || event.getGuild() == null) return;

        if (tolerancia == 0){
            tolerancia = 4; // numero padrao de tolerancia
        }
        int mensagemCacheTol = tolerancia + 2;

        String chave = gerarChave(event); // chave é referente a: id do servidor : id do usuário
        String conteudo = event.getMessage().getContentRaw();

        mensagemCache.putIfAbsent(chave, new ArrayList<>());
        List<String> mensagens = mensagemCache.get(chave);

        mensagens.add(conteudo);

        if (mensagens.size() > mensagemCacheTol) {
            mensagens.remove(0);
        }

        // Verifica se o usuário enviou <tolerancia> mensagens idênticas seguidas
        if (mensagens.size() >= tolerancia) {
            boolean spamou = mensagens.subList(mensagens.size() - tolerancia, mensagens.size())
                    .stream()
                    .distinct()
                    .count() == 1;

            if (spamou) {
                int avisosAtuais = avisosBan(tolerancia, chave); // incrementa

                if (avisosAtuais == tolerancia) {
                    User user = event.getAuthor();
                    userBanService.banUser(event, event.getGuild(), user, 1, "Motivo: Spam");
                    avisos.remove(chave);
                    mensagemCache.remove(chave);
                }
            }
        }

    }

    public int avisosBan(int tolerancia, String chave) {
        int avisosAtuais = avisos.getOrDefault(chave, 0) + 1;

        if (avisosAtuais > tolerancia) {
            System.out.println("BAN CHAVE: " + chave);
        } else {
            avisos.put(chave, avisosAtuais);
        }

        return avisosAtuais;
    }

    public int getAvisos(String chave) {
        return avisos.getOrDefault(chave, 0);
    }

    private String gerarChave(MessageReceivedEvent event) {
        return event.getGuild().getId() + ":" + event.getAuthor().getId(); //separa o id do servidor e do usuario no map
    }

    public String testMAP() {
        StringBuilder sb = new StringBuilder();

        sb.append("\n\n📌 AVISOS:\n");
        avisos.forEach((chave, valor) -> sb.append(" → ").append(chave).append(" = ").append(valor).append("\n"));

        sb.append("\n📌 MENSAGEM CACHE:\n");
        mensagemCache.forEach((chave, mensagens) -> {
            sb.append(" → ").append(chave).append(" = ").append(mensagens.size()).append(" mensagens: ").append(mensagens).append("\n");
        });

        return sb.toString();
    }
}

