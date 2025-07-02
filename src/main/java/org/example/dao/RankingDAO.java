package org.example.dao;

import org.example.models.entities.Ranking;

import org.example.util.DataBaseConexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RankingDAO {

    // Salva ou atualiza um ranking (pontos) de um usuário em um servidor
    public void salvarRanking(Ranking ranking) {
        String sql = "INSERT INTO ranking (guild_id, user_id, user_pontos, daily, ultimo_resgate) VALUES (?, ?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE user_pontos = VALUES(user_pontos)," +
                "daily = VALUES(daily)," +
                "ultimo_resgate = VALUES(ultimo_resgate)";
        try (Connection conn = DataBaseConexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, ranking.getGuild_id());
            stmt.setLong(2, ranking.getUser_id());
            stmt.setInt(3, ranking.getUser_pontos());
            stmt.setBoolean(4, ranking.isDaily());      // Status de daily (true/false)

            // Verifica se há data de último resgate
            if (ranking.getUltimo_resgate() != null) {
                stmt.setDate(5, java.sql.Date.valueOf(ranking.getUltimo_resgate()));
            } else {
                stmt.setDate(5, null);
            }
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Obtém o ranking de um usuário em um servidor
    public Ranking obterRanking(long userId, long guildId) {
        String sql = "SELECT * FROM ranking WHERE user_id = ? AND guild_id = ?";

        try (Connection conn = DataBaseConexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, userId);
            stmt.setLong(2, guildId);

            try (ResultSet rs = stmt.executeQuery()) {

                    if (rs.next()) {
                        LocalDate ultimo_resgate = null;
                        if (rs.getDate("ultimo_resgate") != null) {
                            ultimo_resgate = rs.getDate("ultimo_resgate").toLocalDate();
                        }

                    return new Ranking(
                            rs.getLong("guild_id"),
                            rs.getLong("user_id"),
                            rs.getInt("user_pontos"),
                            rs.getBoolean("daily"),
                            ultimo_resgate
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // Remove um ranking específico de um usuário em um servidor
    public void deletarRanking(long userId, long guildId) {
        String sql = "DELETE FROM ranking WHERE user_id = ? AND guild_id = ?";

        try (Connection conn = DataBaseConexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, userId);
            stmt.setLong(2, guildId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Lista o ranking (top N) de um servidor, ordenado por pontos decrescentes
    public List<Ranking> listarRankingPorGuild(long guildId, int limite) {
        String sql = "SELECT * FROM ranking WHERE guild_id = ? ORDER BY user_pontos DESC LIMIT ?";
        List<Ranking> lista = new ArrayList<>();

        try (Connection conn = DataBaseConexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, guildId);
            stmt.setInt(2, limite);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    LocalDate ultimo_resgate = null;
                    if (rs.getDate("ultimo_resgate") != null) {
                        ultimo_resgate = rs.getDate("ultimo_resgate").toLocalDate();
                    }
                    lista.add(new Ranking(
                            rs.getLong("guild_id"),
                            rs.getLong("user_id"),
                            rs.getInt("user_pontos"),
                            rs.getBoolean("daily"),
                            ultimo_resgate
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}
