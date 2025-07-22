package org.example.util;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConexao {

        private static final String URL = System.getenv("DATABASE_URL");
        private static final String USER = System.getenv("DATABASE_USER");
        private static final String SENHA = System.getenv("DATABASE_SENHA");

        public static Connection getConnection() throws SQLException {
            return DriverManager.getConnection(URL, USER, SENHA);
        }
    }

