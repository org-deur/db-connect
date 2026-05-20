package com.anh.api;

import com.anh.core.metadata.MetadataService;

import io.javalin.Javalin;

public class MetadataController {

    public static void register(
            Javalin app) {

        registerTables(app);

        registerColumns(app);
    }

    private static void registerTables(
            Javalin app) {

        app.get(
                "/metadata/tables/{sessionId}",
                ctx -> {

                    try {

                        String sessionId = ctx.pathParam(
                                "sessionId");

                        ctx.json(MetadataService.getTables(sessionId));

                    } catch (Exception e) {

                        e.printStackTrace();

                        ctx.status(500);

                        ctx.result(e.getMessage());
                    }
                });
    }

    private static void registerColumns(
            Javalin app) {

        app.get(
                "/metadata/columns/{sessionId}/{table}",
                ctx -> {

                    try {

                        String sessionId = ctx.pathParam(
                                "sessionId");

                        String table = ctx.pathParam(
                                "table");

                        ctx.json(MetadataService.getColumns(sessionId, table));

                    } catch (Exception e) {

                        e.printStackTrace();

                        ctx.status(500);

                        ctx.result(e.getMessage());
                    }
                });
    }
}
