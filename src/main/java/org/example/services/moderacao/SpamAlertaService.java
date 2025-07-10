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
        if (event.getAuthor().isBot() || event.getGuild() == null) return;

        String chave = gerarChave(event); // chave é referente a: id do servidor : id do usuário
        String conteudo = event.getMessage().getContentRaw();

        mensagemCache.putIfAbsent(chave, new ArrayList<>());
        List<String> mensagens = mensagemCache.get(chave);

        mensagens.add(conteudo);

        if (mensagens.size() > 9) {
            mensagens.remove(0);
        }

        // Verifica se o usuário enviou 6 mensagens idênticas seguidas
        if (mensagens.size() >= 6) {
            boolean spamou = mensagens.subList(mensagens.size() - 6, mensagens.size())
                    .stream()
                    .distinct()
                    .count() == 1;

            if (spamou) {

                avisosBan(chave); // incrementa primeiro
                int avisosAtuais = getAvisos(chave); // agora pega o valor atualizado

                switch (avisosAtuais) {
                    case 1:
                        event.getChannel().sendMessage(event.getAuthor().getAsMention() + " está spammando mensagens repetidas! Você pode ser punido! <:astra_com_raiva:1390824233243906048>" +
                                "\n[ 1º AVISO ]").queue();
                        break;
                    case 2:
                        event.getChannel().sendMessage(event.getAuthor().getAsMention() + " está spammando mensagens repetidas! Você pode ser punido! <:astra_com_raiva:1390824233243906048>" +
                                "\n[ 2º AVISO ]").queue();
                        break;
                    case 3:
                        event.getChannel().sendMessage(event.getAuthor().getAsMention() + " está spammando mensagens repetidas! Você pode ser punido! <:astra_com_raiva:1390824233243906048>" +
                                "\n[ 3º AVISO ]").queue();
                        break;
                    case 4:
                        System.out.println("Tentando banir usuário: " + event.getAuthor().getAsTag());
                        User user = event.getAuthor();
                        userBanService.banUser(event, event.getGuild(), user, 1, "Motivo de banimento: Spam");
                        break;
                }
            }
        }
    }

    public int avisosBan(String chave) {
        int avisosAtuais = avisos.getOrDefault(chave, 0) + 1;

        if (avisosAtuais > 4) {
            avisos.remove(chave);
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
}

