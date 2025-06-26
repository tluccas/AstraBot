package org.example.views;

import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

public class ReadyListener extends ListenerAdapter {
    @Override
    public void onReady(ReadyEvent event) {
        event.getJDA().getPresence().setActivity(Activity.playing("*help <a:idontcare:1387821007955296256>"));
        System.out.println("Bot está pronto e com presença atualizada!");
        // Registrar comandos slash no void (para testes)
        Guild guild = event.getJDA().getGuildById("1113547243593678979");
        if (guild != null) {
            guild.updateCommands().addCommands(
                    Commands.slash("help", "Exibe os comandos disponíveis"),
                    Commands.slash("ping", "Responde com pong"),
                    Commands.slash("astra", "Vai te responder utilizando IA <a:robo:1387819870321905684>")
                            .addOption(OptionType.STRING, "olá", "Me fale algo", true)
            ).queue();
        }
    }
}
