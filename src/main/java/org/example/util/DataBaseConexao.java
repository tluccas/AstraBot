package org.example.util;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConexao {
        private static final Dotenv dotenv = Dotenv.load();
        private static final String URL = dotenv.get("DATABASE_URL");
        private static final String USER = dotenv.get("DATABASE_USER");
        private static final String SENHA = dotenv.get("DATABASE_SENHA");

        public static Connection getConnection() throws SQLException {
            return DriverManager.getConnection(URL, USER, SENHA);
        }
    }

