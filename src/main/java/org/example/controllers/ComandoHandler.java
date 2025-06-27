package org.example.controllers;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;


import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.example.comandos.*;
import org.example.comandos.testes.TesteBoasVindasComando;
import org.example.dao.GuildJoinMessageDAO;
import org.example.models.Comando;
import org.example.models.ComandoSlash;

public class ComandoHandler {

    private final Map<String, Comando> comandos = new HashMap<>();


    public ComandoHandler() {
        registrarComando(new PongComando());
        registrarComando(new ComandoHelp(ComandoHandler.this));
        registrarComando(new ComandoAI());
        registrarComando(new SetMsgWelcomeComando(new GuildJoinMessageDAO()));
        registrarComando(new SetMsgWelcomeChannelComando(new GuildJoinMessageDAO()));
        // Adicionar novos comandos aqui

        //Comandos de teste
        registrarComando(new TesteBoasVindasComando(new GuildJoinMessageDAO()));
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

    public Map<String, Comando> getComandos() {
        return comandos;
    }
}