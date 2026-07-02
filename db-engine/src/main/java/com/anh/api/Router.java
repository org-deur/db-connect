package com.anh.api;

import io.javalin.Javalin;

public class Router {
    public static void register(Javalin app) {

        HealthController.register(app);

        ConnectionController.register(app);

        QueryController.register(app);

        MetadataController.register(app);

        SavedConnectionController.register(app);
    }
}
