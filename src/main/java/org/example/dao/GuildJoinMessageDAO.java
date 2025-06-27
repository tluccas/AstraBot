package org.example.dao;

import org.example.models.entities.GuildJoinMessage;
import org.example.util.DataBaseConexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GuildJoinMessageDAO {

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
            e.printStackTrace();
        }
    }

    //Salva a mensagem no db
    public void salvarMensagem(long guildId, String mensagem, String imagemurl) {
        String sql = "UPDATE guild_join_message SET mensagem = ?, iamgem_url = ? WHERE guild_id = ?";

        try (Connection conn = DataBaseConexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, mensagem);
            stmt.setString(2, imagemurl);
            stmt.setLong(2, guildId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
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
            e.printStackTrace();
        }
        return null;
    }
}
