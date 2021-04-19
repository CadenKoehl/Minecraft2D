package com.cadenkoehl.minecraft2D.display;

import com.cadenkoehl.minecraft2D.Minecraft2D;

import javax.swing.*;

public class GameFrame extends JFrame {

    public static int WIDTH = 1500;
    public static int HEIGHT = 800;

    public GameFrame() {
        this.add(new GameWindow(this));
        this.setSettings();
        this.setVisible(true);
    }

    public void setSettings() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle(Minecraft2D.TITLE);
        this.setSize(WIDTH, HEIGHT);
        this.setLocationRelativeTo(null);
    }
}
