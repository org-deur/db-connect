package com.anh.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.anh.core.mapper.SavedConnectionMapper;
import com.anh.model.SavedConnection;

public class SavedConnectionRepository extends BaseRepository{
    public List<SavedConnection> findAll() {
        String sql = """
            SELECT
                id,
                name,
                database_type,
                host,
                port,
                database_name,
                username,
                created_at,
                last_used_at
            FROM saved_connection
            ORDER BY name;
                """;
        List<SavedConnection> connections = new ArrayList<>();

        try (
                Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {

                connections.add(SavedConnectionMapper.map(rs));
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return connections;
    };

    public SavedConnection findById(int id) {
        String sql = """
            SELECT
                id,
                name,
                database_type,
                host,
                port,
                database_name,
                username,
                created_at,
                last_used_at
            FROM saved_connection
            WHERE id = ?;
                """;
        try (
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet rs = statement.executeQuery()) {

                if (rs.next()) {

                    return SavedConnectionMapper.map(rs);
                }

                return null;
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    };

    public void save(SavedConnection connection) {
        // String sql = """
        //     INSERT INTO saved_connection
        //     (
        //         name,
        //         database_type,
        //         host,
        //         port,
        //         database_name,
        //         username
        //     )
        //     VALUES
        //     (
        //         ?, ?, ?, ?, ?, ?
        //     );
        //         """;
        // try (
        //     Connection connect = getConnection();
        //     PreparedStatement statement = connect.prepareStatement(sql)) {

        //     statement.setString(1, connection.name);
        //     statement.setString(2, connection.databaseType);
        //     statement.setString(3, connection.host);
        //     statement.setInt(4, connection.port);
        //     statement.setString(5, connection.databaseName);
        //     statement.setString(6, connection.username);

        //     try (ResultSet rs = statement.executeQuery()) {

        //     }

        // } catch (Exception e) {
        //     throw new RuntimeException(e);
        // }
    };

    public void update(SavedConnection connection) {

    };

    public void delete(int id) {

    };

}
