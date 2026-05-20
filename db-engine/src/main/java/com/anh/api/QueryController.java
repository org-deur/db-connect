package com.anh.api;

import com.anh.core.query.QueryService;
import com.anh.dto.QueryRequest;
import com.anh.dto.QueryResponse;

import io.javalin.Javalin;

public class QueryController {
    public static void register(
            Javalin app) {

        app.post("/query", ctx -> {

            QueryResponse response;

            try {

                QueryRequest request = ctx.bodyAsClass(
                        QueryRequest.class);

                response = QueryService.execute(
                        request.sessionId,
                        request.sql);

                ctx.json(response);

            } catch (Exception e) {

                e.printStackTrace();

                response = new QueryResponse();

                response.success = false;

                response.message = e.getMessage();

                ctx.status(500);

                ctx.json(response);
            }
        });
    }
}
