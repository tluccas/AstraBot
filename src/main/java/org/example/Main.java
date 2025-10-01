package org.example;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.example.config.Config;
import org.example.dao.AutoModDAO;
import org.example.views.ready.ReadyAutoModListener;
import org.example.views.BotListener;
import org.example.views.GuildMemberJoinListener;
import org.example.views.ready.ReadyListener;
import org.example.views.SlashComandoListener;

import java.util.concurrent.TimeUnit;


public class Main {
    public static void main(String[] args) {
        final Config config = new Config();
        try {
            AutoModDAO autoModDAO = new AutoModDAO();
            BotListener botListener = new BotListener();
            JDA builder = JDABuilder.createDefault(config.getBot_token(),
                    GatewayIntent.GUILD_MESSAGES,
                    GatewayIntent.GUILD_MEMBERS,
                    GatewayIntent.MESSAGE_CONTENT,
                    GatewayIntent.GUILD_VOICE_STATES,
                    GatewayIntent.GUILD_EMOJIS_AND_STICKERS,
                    GatewayIntent.SCHEDULED_EVENTS).addEventListeners(botListener,
                    new SlashComandoListener(),
                    new ReadyListener(), new GuildMemberJoinListener(), new ReadyAutoModListener(autoModDAO)).build()
            ;
            builder.awaitReady();


            System.out.println("\n\nBot iniciado");
            System.out.println("\n\nServidores conectados: " + builder.getGuilds().size());

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("Desligando executor do BotListener...");
                botListener.getExecutor().shutdown();
                try {
                    if (!botListener.getExecutor().awaitTermination(5, TimeUnit.SECONDS)) {
                        botListener.getExecutor().shutdownNow();
                    }
                } catch (InterruptedException e) {
                    botListener.getExecutor().shutdownNow();
                }
            }));
        } catch (Exception e) {
            System.out.println("Erro ao iniciar o bot: " + e.getMessage());
        }
    }
}