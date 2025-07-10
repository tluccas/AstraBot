package org.example.services.moderação;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpamAlertaService extends ListenerAdapter {

    private final Map<Long, List<String>> mensagemCache = new HashMap<>();

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;

        Long userId = event.getAuthor().getIdLong();
        String conteudo = event.getMessage().getContentRaw();

        mensagemCache.putIfAbsent(userId, new ArrayList<>());
        List<String> mensagens = mensagemCache.get(userId);

        mensagens.add(conteudo);

        if (mensagens.size() > 9) {
            mensagens.remove(0);
        }

        // Verifica se o usuário enviou 6 mensagens idênticas seguidas
        if (mensagens.size() >= 6) {
            boolean spamou = mensagens.subList(mensagens.size() - 6, mensagens.size()) // últimas 6 mensagens
                    .stream()
                    .distinct() // remove as mensagens duplicadas
                    .count() == 1; // se só tiver 1 mensagem distinta, então todas são iguais (spam)
            if (spamou) {
                event.getChannel().sendMessage(event.getAuthor().getAsMention() + " está spammando mensagens repetidas! Você pode ser punido! <:astra_com_raiva:1390824233243906048>").queue();
            }

        }
    }
}

