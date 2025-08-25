package org.example.config;


import io.github.cdimascio.dotenv.Dotenv;

public class Config {

    private static final Dotenv dotenv = Dotenv.load();

    public String getBot_token() {
        return dotenv.get("DISCORD_BOT_TOKEN");
    }
}
