package com.anh.core.configdb;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.Statement;

public class ConfigDatabaseInitializer {

    public static void initialize() {

        try (
                Connection connection = ConfigDatabase.getConnection();
                Statement statement = connection.createStatement()) {

            InputStream inputStream = ConfigDatabaseInitializer.class
                    .getClassLoader()
                    .getResourceAsStream("sql/configdb/v1_init.sql");

            if (inputStream == null) {
                throw new RuntimeException("Cannot find sql/configdb/v1_init.sql");
            }

            String sql = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

            for (String script : sql.split(";")) {

                script = script.trim();

                if (!script.isEmpty()) {
                    statement.execute(script);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
