package com.anh.core.configdb;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConfigDatabase {

    private static final String URL;

    static {
        try {
            // ~/.db-client/config.db (Linux/macOS)
            // C:\Users\<user>\.db-client\config.db (Windows)
            Path dbPath = Paths.get(
                    System.getProperty("user.home"),
                    ".db-client",
                    "config.db"
            );

            System.out.println("Config DB: " + dbPath.toAbsolutePath());

            Files.createDirectories(dbPath.getParent());

            URL = "jdbc:sqlite:" + dbPath.toAbsolutePath();

            Class.forName("org.sqlite.JDBC");

        } catch (Exception e) {
            throw new RuntimeException("Cannot initialize config database.", e);
        }
    }

    private ConfigDatabase() {
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}
