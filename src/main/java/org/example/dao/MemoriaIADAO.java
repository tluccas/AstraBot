package org.example.dao;

import org.example.models.entities.MemoriaIA;
import org.example.util.DataBaseConexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MemoriaIADAO {


    //Método para salvar a memoria de mensagens que o usuário troca com a IA
    public void salvarMemoria(MemoriaIA memoria) {
        String sql = "INSERT INTO ia_memoria (user_id, conteudo, tokens) VALUES (?, ?, ?)" +
                " ON DUPLICATE KEY UPDATE conteudo = VALUES(conteudo), " +
                " tokens = VALUES(tokens)";

        try (Connection conn = DataBaseConexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, memoria.getUser_id());
            stmt.setString(2, memoria.getConteudo());
            stmt.setInt(3, memoria.getTokens());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

        public MemoriaIA obterMemoria(long userID){
            String sql = "SELECT * FROM ia_memoria WHERE user_id = ?";

            try(Connection conn = DataBaseConexao.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)){

                stmt.setLong(1, userID);

                try(ResultSet rs = stmt.executeQuery()){
                    if(rs.next()){

                        return new MemoriaIA(
                                rs.getLong("user_id"),
                                rs.getString("conteudo"),
                                rs.getInt("tokens")
                        );
                    }
                }

            }catch (SQLException e) {
                e.printStackTrace();
            }

            return null;
        }

    public void limparMemoria(long userID) {
        String sql = "UPDATE ia_memoria SET conteudo = '', tokens = 0 WHERE user_id = ?";
        try (Connection conn = DataBaseConexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, userID);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao limpar memória: " + e.getMessage());
        }
    }
    }
