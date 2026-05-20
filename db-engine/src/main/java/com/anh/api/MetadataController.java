package com.anh.api;

import com.anh.core.metadata.MetadataService;

import io.javalin.Javalin;

public class MetadataController {
    public static void register(
            Javalin app) {

        app.get("/metadata/tables/{sessionId}",
                ctx -> {

                    try {

                        String sessionId = ctx.pathParam("sessionId");

                        ctx.json(MetadataService.getTables(sessionId));

                    } catch (Exception e) {

                        e.printStackTrace();

                        ctx.status(500);

                        ctx.result(e.getMessage());
                    }
                });
    }
}
