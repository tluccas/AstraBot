package org.example.services.registro;

import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import java.util.ArrayList;
import java.util.List;

public class SlashCommandRegistryService {

    public static List<CommandData> getComandos(){ //Forma mais dinâmica e limpa de carregar os slashs
                                                  //no ReadyListener

        List<CommandData> comandos = new ArrayList<>();

        comandos.add(Commands.slash("help", "Exibe os comandos disponíveis"));
        comandos.add(Commands.slash("ping", "Responde com pong"));
        comandos.add(Commands.slash("astra", "Vai te responder utilizando IA")
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
        comandos.add(Commands.slash("piada", "Astra te conta uma piada"));
        comandos.add(Commands.slash("meme", "Envia um meme de qualidade duvidosa"));
        comandos.add(Commands.slash("antispam", "Bane usuários automaticamente ao detectar spam"));
        comandos.add(Commands.slash("servidores", "Exibe em quantos servidores a Astra está trabalhando."));
        return comandos;
    }
}
