package org.example;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.example.config.Config;
import org.example.views.BotListener;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws Exception {
        final Config config = new Config();
        try {
            JDA api = JDABuilder.createDefault(config.getBot_token(),
                    GatewayIntent.GUILD_MESSAGES,
                    GatewayIntent.MESSAGE_CONTENT,
                    GatewayIntent.GUILD_VOICE_STATES,
                    GatewayIntent.GUILD_EMOJIS_AND_STICKERS,
                    GatewayIntent.SCHEDULED_EVENTS).addEventListeners(new BotListener()).build();
            System.out.println(api.getGuilds().size());
            System.out.println("Bot iniciado :)");
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}