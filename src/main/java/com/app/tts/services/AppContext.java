package com.app.tts.services;

import org.springframework.context.ApplicationContext;

public class AppContext {

    private static ApplicationContext ctx;

    public AppContext() {
    }

    public static void setApplicationContext(ApplicationContext applicationContext) {
        ctx = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return ctx;
    }
}
