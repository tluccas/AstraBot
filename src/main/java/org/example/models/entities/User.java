package org.example.models.entities;

import java.time.LocalDate;

public class User {

    private long user_id;
    private long guild_id;
    private String user_nome;


    public User(long user_id, long guild_id, String user_nome) {
        this.user_id = user_id;
        this.guild_id = guild_id;
        this.user_nome = user_nome;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getGuild_id() {
        return guild_id;
    }

    public void setGuild_id(long guild_id) {
        this.guild_id = guild_id;
    }

    public String getUser_nome() {
        return user_nome;
    }

    public void setUser_nome(String user_nome) {
        this.user_nome = user_nome;
    }

}
