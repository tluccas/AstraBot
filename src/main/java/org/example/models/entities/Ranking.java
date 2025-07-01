package org.example.models.entities;

public class Ranking {

    private long guild_id;
    private long user_id;
    private int user_pontos;

    public Ranking(long guild_id, long user_id, int user_pontos) {
        this.guild_id = guild_id;
        this.user_id = user_id;
        this.user_pontos = user_pontos;
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

}
