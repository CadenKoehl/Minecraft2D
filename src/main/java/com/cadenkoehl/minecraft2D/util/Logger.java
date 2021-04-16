package com.cadenkoehl.minecraft2D.util;

public class Logger {
    public static void log(LogLevel level, String message) {
        System.err.println("[" + level.name() + "/" + Thread.currentThread().getName().toUpperCase() + "]: " + message);
    }
}
