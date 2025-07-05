package org.example.dao;

import org.example.models.entities.User;
import org.example.util.DataBaseConexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserDAO {

    private static final Logger logger = LoggerFactory.getLogger(UserDAO.class);

    // Salva ou atualiza um usuário no banco
    public void salvarUser(User user) {
        String sql = "INSERT INTO user (user_id, guild_id, user_nome) " +
                "VALUES (?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE " +
                "guild_id = VALUES(guild_id), " +
                "user_nome = VALUES(user_nome)";

        try (Connection conn = DataBaseConexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, user.getUser_id());                      // ID do usuário
            stmt.setLong(2, user.getGuild_id());                    // ID do servidor (guild)
            stmt.setString(3, user.getUser_nome());                 // Nome do usuário

            stmt.executeUpdate();

        } catch (SQLException e) {
            logger.error("[ERRO] Ao salvar USUÁRIO\n\nID: {}\n\nNOME: {}", user.getUser_id(), user.getUser_nome(), e);
        }
    }

    // Busca um usuário pelo ID
    public User obterUser(long userId) {
        String sql = "SELECT * FROM user WHERE user_id = ?";

        try (Connection conn = DataBaseConexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {

                    return new User(
                            rs.getLong("user_id"),
                            rs.getLong("guild_id"),
                            rs.getString("user_nome")

                    );
                }
            }
        } catch (SQLException e) {
            logger.error("[ERRO] Ao obter USUÁRIO: {}", userId, e);
        }

        return null;
    }

    // Remove um usuário do banco
    public void deletarUser(long userId) {
        String sql = "DELETE FROM user WHERE user_id = ?";

        try (Connection conn = DataBaseConexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, userId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            logger.error("[ERRO] ao deletar USUÁRIO: {}", userId, e);
        }
    }
}