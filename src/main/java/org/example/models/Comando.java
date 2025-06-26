package org.example.models;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public interface Comando {

    String getNomeComando(); // Captura o nome do comando
    String getDescricao(); // Descrição do comando
    void executar(MessageReceivedEvent event, String[] args);
}
