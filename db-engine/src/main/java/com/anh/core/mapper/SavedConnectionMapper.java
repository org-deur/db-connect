package com.anh.core.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.anh.model.SavedConnection;

public class SavedConnectionMapper {

    private SavedConnectionMapper() {
    }

    public static SavedConnection map(ResultSet rs) throws SQLException {

        SavedConnection connection = new SavedConnection();

        connection.id = rs.getInt("id");
        connection.name = rs.getString("name");
        connection.databaseType = rs.getString("database_type");
        connection.host = rs.getString("host");
        connection.port = rs.getInt("port");
        connection.databaseName = rs.getString("database_name");
        connection.username = rs.getString("username");
        connection.createdAt = rs.getString("created_at");
        connection.lastUsedAt = rs.getString("last_used_at");

        return connection;
    }
}
