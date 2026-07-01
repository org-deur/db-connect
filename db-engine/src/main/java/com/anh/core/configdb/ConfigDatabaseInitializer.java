package com.anh.core.configdb;

import java.sql.Connection;
import java.sql.Statement;

public class ConfigDatabaseInitializer {

    public static void initialize() {

        try (Connection connection = ConfigDatabase.getConnection();
             Statement statement = connection.createStatement()) {

            // Saved Connections
            statement.execute("""
                CREATE TABLE IF NOT EXISTS saved_connection (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,

                    name TEXT NOT NULL,
                    database_type TEXT NOT NULL,

                    host TEXT,
                    port INTEGER,
                    database_name TEXT,

                    username TEXT,
                --    password TEXT,

                    created_at TEXT DEFAULT CURRENT_TIMESTAMP,
                    last_used_at TEXT
                );
            """);

            // Query History
            statement.execute("""
                CREATE TABLE IF NOT EXISTS query_history (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,

                    connection_id INTEGER NOT NULL,

                    sql TEXT NOT NULL,

                    execution_time_ms INTEGER,

                    executed_at TEXT DEFAULT CURRENT_TIMESTAMP,

                    FOREIGN KEY (connection_id)
                        REFERENCES saved_connection(id)
                        ON DELETE CASCADE
                );
            """);

            // Favorite Queries
            statement.execute("""
                CREATE TABLE IF NOT EXISTS favorite_query (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,

                    connection_id INTEGER,

                    name TEXT NOT NULL,

                    sql TEXT NOT NULL,

                    created_at TEXT DEFAULT CURRENT_TIMESTAMP,

                    FOREIGN KEY (connection_id)
                        REFERENCES saved_connection(id)
                        ON DELETE CASCADE
                );
            """);

            // Application Settings
            statement.execute("""
                CREATE TABLE IF NOT EXISTS application_setting (
                    key TEXT PRIMARY KEY,

                    value TEXT NOT NULL
                );
            """);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
