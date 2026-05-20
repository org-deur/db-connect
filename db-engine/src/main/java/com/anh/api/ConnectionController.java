package com.anh.api;

import com.anh.core.connection.ConnectionManager;
import com.anh.dto.ConnectionRequest;
import com.anh.dto.ConnectionResponse;

import io.javalin.Javalin;

public class ConnectionController {
    public static void register(Javalin app) {

        registerTest(app);

        registerCreate(app);
    }

    private static void registerTest(Javalin app) {

        app.post("/connect/test", ctx -> {

            ConnectionResponse response = new ConnectionResponse();

            try {

                ConnectionRequest request = ctx.bodyAsClass(ConnectionRequest.class);

                ConnectionManager.testConnection(request);

                response.success = true;

                response.message = "Connection successful";

                ctx.json(response);

            } catch (Exception e) {

                e.printStackTrace();

                ctx.status(500);

                response.success = false;

                response.message = e.getMessage();

                ctx.json(response);
            }
        });
    }

    private static void registerCreate(Javalin app) {

        app.post("/connect/create", ctx -> {

            ConnectionResponse response = new ConnectionResponse();

            try {

                ConnectionRequest request = ctx.bodyAsClass(ConnectionRequest.class);

                String sessionId = ConnectionManager.createConnection(request);

                response.success = true;

                response.message = "Session created";

                response.sessionId = sessionId;

                ctx.json(response);

            } catch (Exception e) {

                e.printStackTrace();

                ctx.status(500);

                response.success = false;

                response.message = e.getMessage();

                ctx.json(response);
            }
        });
    }
}
