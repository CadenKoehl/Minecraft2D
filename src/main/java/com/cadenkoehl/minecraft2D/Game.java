package com.cadenkoehl.minecraft2D;

import com.cadenkoehl.minecraft2D.display.GameFrame;
import com.cadenkoehl.minecraft2D.display.GameWindow;

import java.awt.*;

public abstract class Game {

    private final GameFrame frame;
    private final GameWindow window;

    public Game() {
        this.frame = new GameFrame();
        this.window = new GameWindow(this, frame);
        frame.add(window);
        frame.setVisible(true);
        this.init();
    }

    public GameFrame getFrame() {
        return frame;
    }

    public GameWindow getWindow() {
        return window;
    }

    public abstract void init();
    public abstract void tick();
    public abstract void renderFrame(Graphics g);
    public abstract void stop();
}
