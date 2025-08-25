package org.example.config;



public class Config {



    public String getBot_token() {
        return System.getenv("DISCORD_BOT_TOKEN");
    }
}
