package com.anh.api;

import com.anh.model.SavedConnection;
import com.anh.repository.SavedConnectionRepository;

import io.javalin.Javalin;

public class SavedConnectionController {

    private static final SavedConnectionRepository repository =
            new SavedConnectionRepository();

    public static void register(Javalin app) {

        registerList(app);

        registerGet(app);

        // registerCreate(app);

        // registerUpdate(app);

        // registerDelete(app);
    }

    private static void registerGet(Javalin app) {
        app.get("/saved-connections/{id}", ctx -> {

            int id = Integer.parseInt(
                    ctx.pathParam("id"));

            SavedConnection connection =
                    repository.findById(id);

            if (connection == null) {

                ctx.status(404);

                return;
            }

            ctx.json(connection);

        });

    }

    private static void registerDelete(Javalin app) {

    }

    private static void registerUpdate(Javalin app) {

    }

    private static void registerCreate(Javalin app) {

    }

    private static void registerList(Javalin app) {
        app.get("/saved-connections", ctx -> {

            ctx.json(repository.findAll());

        });
    }

}
