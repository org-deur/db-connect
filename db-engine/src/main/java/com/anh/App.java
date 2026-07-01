package com.anh;

import com.anh.api.Router;
import com.anh.core.configdb.ConfigDatabaseInitializer;

import io.javalin.Javalin;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args )
    {
        ConfigDatabaseInitializer.initialize();
        
        Javalin app = Javalin.create(config -> {

            config.bundledPlugins.enableCors(cors -> {
                cors.addRule(rule -> {
                    rule.anyHost();
                });
            });

            // config.bundledPlugins.enableCors(cors -> {
            //     cors.addRule(rule -> {
            //         rule.allowHost("http://localhost:1420");
            //         rule.allowHost("http://localhost:5173");
            //     });
            // });
        });

        app.start(8080);

        Router.register(app);

        System.out.println("Backend started");

    }
}
