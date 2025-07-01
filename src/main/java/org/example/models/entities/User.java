package org.example.models.entities;

import java.time.LocalDate;

public class User {

    private long user_id;
    private long guild_id;
    private String user_nome;
    private String user_avatar;
    private boolean daily; //Boolean para controle de resgate diário
    private LocalDate ultimo_resgate;


    public User(long user_id, long guild_id, String user_nome, String user_avatar, boolean daily, LocalDate ultimo_resgate) {
        this.user_id = user_id;
        this.guild_id = guild_id;
        this.user_nome = user_nome;
        this.user_avatar = user_avatar;
        this.daily = daily;
        this.ultimo_resgate = ultimo_resgate;
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

    public String getUser_avatar() {
        return user_avatar;
    }

    public void setUser_avatar(String user_avatar) {
        this.user_avatar = user_avatar;
    }

    public boolean isDaily() {
        return daily;
    }

    public void setDaily(boolean daily) {
        this.daily = daily;
    }

    public LocalDate getUltimo_resgate() {
        return ultimo_resgate;
    }

    public void setUltimo_resgate(LocalDate ultimo_resgate) {
        this.ultimo_resgate = ultimo_resgate;
    }
}
