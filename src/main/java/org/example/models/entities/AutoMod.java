package org.example.models.entities;

public class AutoMod {

    private long guild_id;

    private boolean spam_mod;

    public AutoMod(long guild_id, boolean spam_mod) {
        this.guild_id = guild_id;
        this.spam_mod = spam_mod;
    }

    public long getGuild_id() {
        return guild_id;
    }

    public void setGuild_id(long guild_id) {
        this.guild_id = guild_id;
    }

    public boolean getSpam_mod() {
        return spam_mod;
    }

    public void setSpam_mod(boolean spam_mod) {
        this.spam_mod = spam_mod;
    }
}
