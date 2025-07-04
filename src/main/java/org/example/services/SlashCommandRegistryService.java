package org.example.services;

import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.example.dao.GuildDAO;
import org.example.models.entities.GuildModel;

import java.util.ArrayList;
import java.util.List;

public class SlashCommandRegistryService {

    public static List<CommandData> getComandos(){ //Forma mais dinâmica e limpa de carregar os slashs
                                                  //no ReadyListener

        List<CommandData> comandos = new ArrayList<>();

        comandos.add(Commands.slash("help", "Exibe os comandos disponíveis"));
        comandos.add(Commands.slash("ping", "Responde com pong"));
        comandos.add(Commands.slash("astra", "Vai te responder utilizando IA <a:robo:1387819870321905684>")
                .addOption(OptionType.STRING, "olá", "Me fale algo", true));
        comandos.add(Commands.slash("setcanalboasvindas", "Define o canal onde as mensagens de boas vindas serão enviadas")
                .addOption(OptionType.CHANNEL, "canal", "Canal de boas-vindas", true));
        comandos.add(Commands.slash("setboasvindas", "Define sua mensagem de boas-vindas personalizada")
                .addOption(OptionType.STRING, "mensagem", "Mensagem de boas-vindas (use {user} para mencionar o usuário), adicione uma imagem ou gif (opcional)", true)
                .addOption(OptionType.STRING, "imagem", "URL da imagem que será exibida (opcional)", false));
        comandos.add(Commands.slash("limpar", "Limpa uma quantidade escolhida de mensagens (máx 100 mensagens)")
                .addOption(OptionType.INTEGER, "quantidade", "Quantidade de mensagens", true));
        comandos.add(Commands.slash("daily", "Resgate seus pontos diários"));
        comandos.add(Commands.slash("rank", "Exibe o ranking do servidor"));

        return comandos;
    }
}
