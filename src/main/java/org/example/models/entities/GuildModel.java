package org.example.models.entities;

public class GuildModel { //Model de servidores para o dao

    private long id; // id do servidor para identificação

    private String nome; //nome do server

    public GuildModel(long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
