package org.example.controllers;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;


import java.util.HashMap;
import java.util.Map;
import org.example.comandos.*;
import org.example.models.Comando;

public class ComandoHandler {

    private final Map<String, Comando> comandos = new HashMap<>();

    public ComandoHandler() {
        registrarComando(new PongComando());
        registrarComando(new ComandoHelp(ComandoHandler.this));
        // Adicionar novos comandos aqui
    }

    private void registrarComando(Comando comando) {
        comandos.put(comando.getNomeComando(), comando);

    }

    public void handle(MessageReceivedEvent event) {
        String[] input = event.getMessage().getContentRaw().split("\\s+");
        String prefix = "*";
        String nomeComando = input[0].substring(prefix.length());

        Comando comando = comandos.get(nomeComando);
        if (comando != null) {
            String[] args = new String[input.length - 1];
            System.arraycopy(input, 1, args, 0, args.length);
            comando.executar(event, args);
        }
    }

    public Map<String, Comando> getComandos() {
        return comandos;
    }
}