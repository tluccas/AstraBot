package org.example;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.example.config.Config;
import org.example.views.BotListener;
import org.example.views.GuildMemberJoinListener;
import org.example.views.ReadyListener;
import org.example.views.SlashComandoListener;

import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        final Config config = new Config();
        try {

            JDA builder = JDABuilder.createDefault(config.getBot_token(),
                    GatewayIntent.GUILD_MESSAGES,
                    GatewayIntent.GUILD_MEMBERS,
                    GatewayIntent.MESSAGE_CONTENT,
                    GatewayIntent.GUILD_VOICE_STATES,
                    GatewayIntent.GUILD_EMOJIS_AND_STICKERS,
                    GatewayIntent.SCHEDULED_EVENTS).addEventListeners(new BotListener(),
                    new SlashComandoListener(),
                    new ReadyListener(), new GuildMemberJoinListener()).build();
            builder.awaitReady();


            System.out.println("\n\nBot iniciado");
            System.out.println("\n\nServidores conectados: " + builder.getGuilds().size());

        } catch (Exception e) {
            System.out.println("Erro ao iniciar o bot: " + e.getMessage());
        }
    }
}