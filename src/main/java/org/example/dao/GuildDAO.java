package org.example.dao;

import org.example.models.entities.GuildModel;
import org.example.util.DataBaseConexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GuildDAO {

    private static final Logger logger = LoggerFactory.getLogger(GuildDAO.class);

    // Salva ou atualiza uma guild no banco
    public void salvarGuild(GuildModel guildModel) {
        String sql = "INSERT INTO guild (guild_id, nome) VALUES (?, ?) " +
                "ON DUPLICATE KEY UPDATE nome = VALUES(nome)";

        try (Connection conn = DataBaseConexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, guildModel.getId());
            stmt.setString(2, guildModel.getNome());
            stmt.executeUpdate();

        } catch (SQLException e) {
            logger.error("[ERRO] ao salvar servidor {} \nID: {}", guildModel.getNome(), guildModel.getId(), e);
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
            logger.error("[ERRO] ao obter servidor {}", e.getMessage());
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
            logger.error("[ERRO] ao deletar servidor {}", guildId, e);
        }
    }
}
