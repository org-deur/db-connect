package com.anh.dto;

import java.util.List;
import java.util.Map;

public class QueryResponse {
    public boolean success;

    public String message;

    public boolean hasResultSet;

    public Integer affectedRows;

    public List<String> columns;

    public List<Map<String, Object>> rows;
}
