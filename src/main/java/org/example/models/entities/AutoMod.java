package org.example.models.entities;

public class AutoMod {

    private long guild_id;

    private boolean spam_mod;

    private boolean welcome_auto_role;

    public AutoMod(long guild_id, boolean spam_mod, boolean welcome_auto_role) {
        this.guild_id = guild_id;
        this.spam_mod = spam_mod;
        this.welcome_auto_role = welcome_auto_role;
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

    public boolean getWelcome_auto_role() {
        return welcome_auto_role;
    }

    public void setWelcome_auto_role(boolean welcome_auto_role) {
        this.welcome_auto_role = welcome_auto_role;
    }
}
