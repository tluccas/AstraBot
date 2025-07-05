package org.example.dao;

import org.example.models.entities.GuildJoinMessage;
import org.example.util.DataBaseConexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GuildJoinMessageDAO {

    private static final Logger logger = LoggerFactory.getLogger(GuildJoinMessageDAO.class);

    public void salvarCanal(long guildId, long canalId) {
        String sql = "INSERT INTO guild_join_message (guild_id, canal_id) VALUES (?, ?) " +
                "ON DUPLICATE KEY UPDATE canal_id = ?";
        try (Connection conn = DataBaseConexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, guildId);
            stmt.setLong(2, canalId);
            stmt.setLong(3, canalId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("[ERRO] ao salvar Canal {} do Servidor: {}", canalId, guildId, e);
        }
    }

    //Salva a mensagem no db
    public void salvarMensagem(long guildId, String mensagem, String imagemurl) {
        String sql = "UPDATE guild_join_message SET mensagem = ?, imagem_url = ? WHERE guild_id = ?";

        try (Connection conn = DataBaseConexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, mensagem);
            stmt.setString(2, imagemurl);
            stmt.setLong(3, guildId);

            int linhasAfetadas = stmt.executeUpdate();
            System.out.println("Linhas afetadas: " + linhasAfetadas);

        } catch (SQLException e) {
            logger.error("[ERRO] ao salvar Mensagem de Welcome do servidor {}", guildId, e);
        }
    }

    public GuildJoinMessage obterPorGuildId(long guildId) {
        String sql = "SELECT mensagem, canal_id, imagem_url FROM guild_join_message WHERE guild_id = ?";
        try (Connection conn = DataBaseConexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, guildId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new GuildJoinMessage(
                            guildId,
                            rs.getString("mensagem"),
                            rs.getLong("canal_id"),
                            rs.getString("imagem_url")
                    );
                }
            }
        } catch (SQLException e) {
            logger.error("[ERRO] ao obter Mensagem de Welcome do Servidor: {}", guildId, e);
        }
        return null;
    }
}
