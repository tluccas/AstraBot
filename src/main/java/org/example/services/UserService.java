package org.example.services;

import java.sql.*;
import java.time.LocalDate;

public class UserService {

    //Aplicação futura na logica de comando comando daily
    public boolean podeFazerDaily(long userId, Connection conn) throws SQLException {
        LocalDate hoje = LocalDate.now();

        // Verifica o ultimo resgate
        String sql = "SELECT ultima_resgate FROM user WHERE user_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Date ultima = rs.getDate("ultimo_resgate");
                if (ultima != null && ultima.toLocalDate().equals(hoje)) {
                    return false; // já fez a daily hoje
                }
            }
        }

        // Atualiza a data do último resgate para hoje
        String update = "UPDATE user SET ultima_resgate = ? WHERE user_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(update)) {
            stmt.setDate(1, Date.valueOf(hoje));
            stmt.setLong(2, userId);
            stmt.executeUpdate();
        }

        return true;
    }
}
