package org.example.models.entities;

public class MemoriaIA {

    private long user_id;
    private String conteudo;
    private int tokens;

    public MemoriaIA(long user_id, String conteudo, int tokens) {
        this.user_id = user_id;
        this.conteudo = conteudo;
        this.tokens = tokens;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public int getTokens() {
        return tokens;
    }

    public void setTokens(int tokens) {
        this.tokens = tokens;
    }
}
