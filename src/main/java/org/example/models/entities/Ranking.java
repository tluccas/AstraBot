package org.example.models.entities;

import java.time.LocalDate;

public class Ranking {

    private long guild_id;
    private long user_id;
    private int user_pontos;
    private boolean daily;
    private LocalDate ultimo_resgate;



    public Ranking(long guild_id, long user_id, int user_pontos, boolean daily, LocalDate ultimo_resgate) {
        this.guild_id = guild_id;
        this.user_id = user_id;
        this.user_pontos = user_pontos;
        this.daily = daily;
        this.ultimo_resgate = ultimo_resgate;
    }
    public long getGuild_id() {
        return guild_id;
    }

    public void setGuild_id(long guild_id) {
        this.guild_id = guild_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public int getUser_pontos() {
        return user_pontos;
    }

    public void setUser_pontos(int user_pontos) {
        this.user_pontos = user_pontos;
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
