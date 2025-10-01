package org.example.config;

public class Config {

    private static final String token = System.getenv("DISCORD_BOT_TOKEN");

    public String getBot_token() {
        return token;
    }
}
