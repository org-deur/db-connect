package com.anh.core.metadata;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.anh.core.connection.ConnectionManager;
import com.anh.model.ColumnInfo;
import com.anh.model.ConnectionSession;
import com.anh.model.TableInfo;

public class MetadataService {
    public static List<TableInfo> getTables(
            String sessionId) throws Exception {

        ConnectionSession session = ConnectionManager.getSession(
                sessionId);

        if (session == null) {

            throw new RuntimeException(
                    "Session not found");
        }

        List<TableInfo> tables = new ArrayList<>();

        try (
                Connection connection = session.dataSource
                        .getConnection()) {

            DatabaseMetaData metaData = connection.getMetaData();

            ResultSet resultSet = metaData.getTables(
                    null,
                    null,
                    "%",
                    new String[] {
                            "TABLE"
                    });

            while (resultSet.next()) {

                TableInfo table = new TableInfo();

                table.schema = resultSet.getString(
                        "TABLE_SCHEM");

                table.name = resultSet.getString(
                        "TABLE_NAME");

                table.type = resultSet.getString(
                        "TABLE_TYPE");

                tables.add(table);
            }
        }

        return tables;
    }

    public static List<ColumnInfo> getColumns(String sessionId, String tableName) throws Exception {

        ConnectionSession session = ConnectionManager.getSession(
                sessionId);

        if (session == null) {

            throw new RuntimeException(
                    "Session not found");
        }

        List<ColumnInfo> columns = new ArrayList<>();

        try (
                Connection connection = session.dataSource
                        .getConnection()) {

            DatabaseMetaData metaData = connection.getMetaData();

            ResultSet resultSet = metaData.getColumns(
                    null,
                    null,
                    tableName,
                    "%");

            while (resultSet.next()) {

                ColumnInfo column = new ColumnInfo();

                column.name = resultSet.getString(
                        "COLUMN_NAME");

                column.type = resultSet.getString(
                        "TYPE_NAME");

                column.size = resultSet.getInt(
                        "COLUMN_SIZE");

                column.nullable = resultSet.getInt(
                        "NULLABLE") == DatabaseMetaData.columnNullable;

                columns.add(column);
            }
        }

        return columns;
    }
}
