package com.anh;

import com.anh.api.Router;

import io.javalin.Javalin;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args )
    {
        Javalin app = Javalin.create();

        app.start(8080);

        Router.register(app);

        System.out.println("Backend started");

    }
}
