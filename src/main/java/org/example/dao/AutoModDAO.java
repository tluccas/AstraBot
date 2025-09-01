package org.example.dao;

import org.example.models.entities.AutoMod;
import org.example.util.DataBaseConexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AutoModDAO {

    private static final Logger logger = LoggerFactory.getLogger(AutoModDAO.class);

    // Salva ou atualiza uma guild no banco
    public void salvarAutoMod(AutoMod autoMod) {
        String sql = "INSERT INTO guild_auto_mod (guild_id,spam_mod, welcome_auto_role) VALUES (?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE spam_mod = VALUES(spam_mod)," +
                "welcome_auto_role = VALUES(welcome_auto_role)";

        try (Connection conn = DataBaseConexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, autoMod.getGuild_id());
            stmt.setBoolean(2, autoMod.getSpam_mod());
            stmt.executeUpdate();

        } catch (SQLException e) {
            logger.error("[ERRO] ao salvar AutoMod \nID: {}", autoMod.getGuild_id(), e);
        }
    }

    // Busca uma guild pelo "id"
    public AutoMod obterAutoMod(long guildId) {
        String sql = "SELECT guild_id, spam_mod FROM guild_auto_mod WHERE guild_id = ?";

        try (Connection conn = DataBaseConexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, guildId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new AutoMod(
                            rs.getLong("guild_id"),
                            rs.getBoolean("spam_mod"),
                            rs.getBoolean("welcome_auto_role")
                    );
                }
            }
        } catch (SQLException e) {
            logger.error("[ERRO] ao obter AutoMod {}", e.getMessage());
        }
        return null;
    }

    // Remove uma guild do banco
    public void deletarAutoMod(long guildId) {
        String sql = "DELETE FROM guild_auto_mod WHERE guild_id = ?";

        try (Connection conn = DataBaseConexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, guildId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            logger.error("[ERRO] ao deletar AutoMod {}", guildId, e);
        }
    }
}
