package org.example.models.entities;

public class GuildJoinMessage {

    private long guildId;
    private String mensagem;
    private String imagem_url;
    private long canalId;

    public GuildJoinMessage(long guildId, String mensagem, long canalId, String imagem_url) {
        this.guildId = guildId;
        this.mensagem = mensagem;
        this.canalId = canalId;
        this.imagem_url = imagem_url;
    }

    public long getGuildId() {
        return guildId;
    }

    public void setGuildId(long guildId) {
        this.guildId = guildId;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public long getCanalId() {
        return canalId;
    }

    public void setCanalId(long canalId) {
        this.canalId = canalId;
    }

    public String getImagem_url() {
        return imagem_url;
    }

    public void setImagem_url(String imagem_url) {
        this.imagem_url = imagem_url;
    }
}
