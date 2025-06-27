package org.example.views;

import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import org.example.dao.GuildDAO;
import org.example.models.entities.GuildModel;
import org.jetbrains.annotations.NotNull;

public class ReadyListener extends ListenerAdapter {
    @Override
    public void onReady(ReadyEvent event) {

        GuildDAO dao = new GuildDAO();

        for (Guild guild : event.getJDA().getGuilds()) {
            GuildModel guildModel = new GuildModel(guild.getIdLong(), guild.getName());
            dao.salvarGuild(guildModel);
        } //atualiza os servidores em que Astra entrou

        System.out.println("Servidores salvos no banco.");
        event.getJDA().getPresence().setActivity(Activity.playing("*help \uD83E\uDD16"));
        System.out.println("Bot está pronto e com presença atualizada!");
       /* Guild guild = event.getJDA().getGuildById("1113547243593678979"); // seu servidor de testes

        if (guild != null) {*/
        event.getJDA().updateCommands().addCommands(
                Commands.slash("help", "Exibe os comandos disponíveis"),
                Commands.slash("ping", "Responde com pong"),
                Commands.slash("astra", "Vai te responder utilizando IA <a:robo:1387819870321905684>")
                        .addOption(OptionType.STRING, "olá", "Me fale algo", true),
                Commands.slash("setcanalboasvindas", "Define o canal onde as mensagens " +
                        "de boas vindas serão enviadas").addOption(OptionType.CHANNEL, "canal", "Canal de boas-vindas", true),
                Commands.slash("setboasvindas", "Define sua mensagem de boas-vindas personalizada")
                        .addOption(OptionType.STRING, "mensagem", "Mensagem de boas-vindas (use {user} para mencionar o usuário), adicione uma imagem ou gif (opcional)", true)
                        .addOption(OptionType.STRING, "imagem", "URL da imagem que será exibida (opcional)", false)

        ).queue();

    }

    @Override
    public void onGuildJoin(GuildJoinEvent event) { // Salva o servidor no banco sempre que astra entrar
        Guild guild = event.getGuild();
        new GuildDAO().salvarGuild(new GuildModel(guild.getIdLong(), guild.getName()));
    }

    @Override
    public void onGuildLeave(GuildLeaveEvent event) {
        Guild guild = event.getGuild();
        new GuildDAO().deletarGuild(new GuildModel(guild.getIdLong(), guild.getName()).getId());
        System.out.println("Fui removida do servidor: " + guild.getName());

    }
}
