package com.anh.core.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.anh.database.common.DatabaseDialect;
import com.anh.database.common.DialectFactory;
import com.anh.dto.ConnectionRequest;
import com.anh.dto.SessionInfoResponse;
import com.anh.model.ConnectionSession;

public class ConnectionManager {

    private static final ConcurrentHashMap<String, ConnectionSession> sessions = new ConcurrentHashMap<>();

    public static String createConnection(ConnectionRequest request) throws SQLException {

        DatabaseDialect dialect = DialectFactory.getDialect(request.type);

        String jdbcUrl = dialect.buildJdbcUrl(
                request.host,
                request.port,
                request.database);

        Connection connection = DriverManager.getConnection(
                jdbcUrl,
                request.username,
                request.password
            );

        String sessionId = UUID.randomUUID().toString();

        ConnectionSession session = new ConnectionSession();

        session.sessionId = sessionId;

        session.type = request.type;

        session.host = request.host;

        session.port = request.port;

        session.database = request.database;

        session.username = request.username;

        session.connection = connection;

        sessions.put(sessionId, session);

        return sessionId;
    }

    public static void testConnection(ConnectionRequest request) throws Exception {

        DatabaseDialect dialect = DialectFactory.getDialect(request.type);

        String jdbcUrl = dialect.buildJdbcUrl(
                request.host,
                request.port,
                request.database);

        Connection connection = DriverManager.getConnection(
                jdbcUrl,
                request.username,
                request.password);

        connection.close();
    }

    public static ConnectionSession getSession(String sessionId) {

        return sessions.get(sessionId);
    }

    public static Collection<ConnectionSession> getSessions() {

        return sessions.values();
    }

    public static void closeSession(String sessionId) throws SQLException {

        ConnectionSession session = sessions.remove(sessionId);

        if (session != null) {

            session.connection.close();
        }
    }

    public static List<SessionInfoResponse> getSessionInfos() {

        List<SessionInfoResponse> result = new ArrayList<>();

        for (ConnectionSession session : sessions.values()) {

            SessionInfoResponse info = new SessionInfoResponse();

            info.sessionId = session.sessionId;

            info.type = session.type;

            info.host = session.host;

            info.port = session.port;

            info.database = session.database;

            info.username = session.username;

            result.add(info);
        }

        return result;
    }
}
