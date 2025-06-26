package org.example.comandos;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.example.models.Comando;

public class PongComando implements Comando {
    @Override
    public String getNomeComando() {
        return "ping";
    }

    @Override
    public String getDescricao() {
        return "Retorna Pong :)";
    }

    @Override
    public void executar(MessageReceivedEvent event, String[] args) {
           String user = event.getAuthor().getName().toLowerCase();
            event.getChannel().sendMessage("Pong! " + "**"+user+"**").queue();
    }
}
