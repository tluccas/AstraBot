package org.example.controllers;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;


import java.util.*;

import org.example.comandos.*;
import org.example.models.Comando;
import org.example.services.registro.CommandRegistryService;

public class ComandoHandler {

    private final Map<String, Comando> comandos = new HashMap<>();
    private static JDA builder;


    public ComandoHandler(JDA builder) {
        this.builder = builder;
        List<Comando> comandosList = CommandRegistryService.getComandos();
        for (Comando c : comandosList) {
            registrarComando(c);
        }

        registrarComando(new ComandoHelp(ComandoHandler.this));
        registrarComando(new CountComando(builder));
    }


    private void registrarComando(Comando comando) {
        comandos.put(comando.getNomeComando().toLowerCase(), comando);

    }


    public void handlePrefixo(MessageReceivedEvent event) {
        String prefixo = "*";
        String conteudo = event.getMessage().getContentRaw().trim();

        if (!conteudo.startsWith(prefixo)) return;

        // Remove o prefixo e separa o restante
        String[] partes = conteudo.substring(prefixo.length()).split("\\s+", 2); // divide em: comando e argumentos (conteudo da mensagem)

        String nomeComando = partes[0].toLowerCase(); // garante que o comando esteja sempre em lowercase
        // Se tiver argumentos após o comando, divide por espaço senão, cria um array vazio.
        String[] args = (partes.length > 1) ? partes[1].split("\\s+") : new String[0];

        Comando comando = comandos.get(nomeComando);
        if (comando != null) {
            comando.executar(event, args);
        }
    }

    public void handleSlash(SlashCommandInteractionEvent event, String nomeComando) {
        Comando comando = comandos.get(nomeComando);
        if (comando != null) {
            comando.executarSlash(event);
            // Um método novo que você cria no seu Comando para Slash
        } else {
            event.reply("Comando não encontrado.").setEphemeral(true).queue();
        }
    }

    public TreeMap<String, Comando> getComandos() {
        return new TreeMap<>(comandos);
    }
}