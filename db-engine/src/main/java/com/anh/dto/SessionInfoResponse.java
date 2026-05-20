package com.anh.dto;

import com.anh.database.common.DatabaseType;

public class SessionInfoResponse {
    public String sessionId;

    public DatabaseType type;

    public String host;

    public int port;

    public String database;

    public String username;
}
