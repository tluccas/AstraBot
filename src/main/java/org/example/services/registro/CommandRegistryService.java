package org.example.services.registro;

import org.example.comandos.*;
import org.example.comandos.diversos.MemeComando;
import org.example.comandos.diversos.PiadaComando;
import org.example.comandos.moderacao.SpamBanEnable;
import org.example.dao.GuildJoinMessageDAO;
import org.example.dao.RankingDAO;
import org.example.dao.UserDAO;
import org.example.models.Comando;

import java.util.ArrayList;
import java.util.List;

public class CommandRegistryService {

    public static List<Comando> getComandos() {
        List<Comando> comandos = new ArrayList<Comando>();

        // Adicionar novos comandos aqui
        comandos.add(new PongComando());
        comandos.add(new ComandoAI());
        comandos.add(new SetMsgWelcomeComando(new GuildJoinMessageDAO()));
        comandos.add(new SetMsgWelcomeChannelComando(new GuildJoinMessageDAO()));
        comandos.add(new LimparComando());
        comandos.add(new DailyComando(new UserDAO(), new RankingDAO()));
        comandos.add(new RankingComando(new RankingDAO()));
        comandos.add(new PiadaComando());
        comandos.add(new MemeComando());
        comandos.add(new SpamBanEnable());

        return comandos;
    }
}
