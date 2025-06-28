package org.example.services;

import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import org.example.comandos.*;
import org.example.comandos.testes.TesteBoasVindasComando;
import org.example.controllers.ComandoHandler;
import org.example.dao.GuildJoinMessageDAO;
import org.example.models.Comando;

import java.util.ArrayList;
import java.util.List;

public class CommandRegistryService {

    public static List<Comando> getComandos() {
        List<Comando> comandos = new ArrayList<Comando>();

        comandos.add(new PongComando());
        comandos.add(new ComandoAI());
        comandos.add(new SetMsgWelcomeComando(new GuildJoinMessageDAO()));
        comandos.add(new SetMsgWelcomeChannelComando(new GuildJoinMessageDAO()));
        comandos.add(new LimparComando());
        // Adicionar novos comandos aqui

        //Comandos de teste
        comandos.add(new TesteBoasVindasComando(new GuildJoinMessageDAO()));

        return comandos;
    }
}
