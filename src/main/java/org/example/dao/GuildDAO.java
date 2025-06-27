package org.example.dao;

import org.example.models.entities.GuildModel;
import org.example.util.DataBaseConexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GuildDAO {

    // Salva ou atualiza uma guild no banco
    public void salvarGuild(GuildModel guildModel) {
        String sql = "REPLACE INTO guild (guild_id, nome) VALUES (?, ?)";

        try (Connection conn = DataBaseConexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, guildModel.getId());
            stmt.setString(2, guildModel.getNome());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Busca uma guild pelo id
    public GuildModel obterGuild(long guildId) {
        String sql = "SELECT guild_id, nome FROM guild WHERE guild_id = ?";

        try (Connection conn = DataBaseConexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, guildId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new GuildModel(
                            rs.getLong("guild_id"),
                            rs.getString("nome")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Remove uma guild do banco
    public void deletarGuild(long guildId) {
        String sql = "DELETE FROM guild WHERE guild_id = ?";

        try (Connection conn = DataBaseConexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, guildId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
