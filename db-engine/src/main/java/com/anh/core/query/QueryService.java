package com.anh.core.query;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.anh.core.connection.ConnectionManager;
import com.anh.dto.QueryResponse;
import com.anh.model.ConnectionSession;

public class QueryService {
    public static QueryResponse execute(
            String sessionId,
            String sql) throws Exception {

        ConnectionSession session = ConnectionManager.getSession(
                sessionId);

        if (session == null) {

            throw new RuntimeException(
                    "Session not found");
        }

        QueryResponse response = new QueryResponse();

        Connection connection = session.connection;

        try (Statement statement = connection.createStatement()) {

            boolean hasResultSet = statement.execute(sql);

            response.success = true;

            response.hasResultSet = hasResultSet;

            if (hasResultSet) {

                ResultSet resultSet = statement.getResultSet();

                ResultSetMetaData metaData = resultSet.getMetaData();

                int columnCount = metaData.getColumnCount();

                List<String> columns = new ArrayList<>();

                List<Map<String, Object>> rows = new ArrayList<>();

                for (int i = 1; i <= columnCount; i++) {

                    columns.add(
                            metaData.getColumnLabel(i));
                }

                while (resultSet.next()) {

                    Map<String, Object> row = new HashMap<>();

                    for (int i = 1; i <= columnCount; i++) {

                        row.put(
                                metaData.getColumnLabel(i),
                                resultSet.getObject(i));
                    }

                    rows.add(row);
                }

                response.columns = columns;

                response.rows = rows;

            } else {

                response.affectedRows = statement.getUpdateCount();
            }
        }

        return response;
    }
}
