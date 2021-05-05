package com.cadenkoehl.minecraft2D.server;

import java.io.IOException;
import java.net.ServerSocket;

public abstract class GameServer {

    private final ServerSocket socket;

    public GameServer() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::stop, "Shutdown thread"));

        this.socket = this.createSocket();
    }

    public abstract void init();
    public abstract void tick();
    public abstract void acceptClient();
    public abstract void stop();

    private ServerSocket createSocket() {
        try {
            return new ServerSocket();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
