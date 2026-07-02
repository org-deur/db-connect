package com.anh.model;

import java.sql.Connection;

import com.anh.database.common.DatabaseType;

public class ConnectionSession {
    public String sessionId;

    public DatabaseType type;

    public String host;

    public int port;

    public String database;

    public String username;

    public Connection connection;
}
