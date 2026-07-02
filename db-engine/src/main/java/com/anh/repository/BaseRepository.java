package com.anh.repository;

import java.sql.Connection;
import java.sql.SQLException;

import com.anh.core.configdb.ConfigDatabase;

public abstract class BaseRepository {
    protected Connection getConnection() throws SQLException {
        return ConfigDatabase.getConnection();
    }
}
