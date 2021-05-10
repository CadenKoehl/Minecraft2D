package com.cadenkoehl.minecraft2D.display;

import com.cadenkoehl.minecraft2D.client.Minecraft2D;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GameFrame extends JFrame {

    public static int WIDTH = 1000;
    public static int HEIGHT = 600;

    private static final BufferedImage CURSOR_IMAGE = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
    public static final Cursor BLANK_CURSOR = Toolkit.getDefaultToolkit().createCustomCursor(CURSOR_IMAGE, new Point(0, 0), "blank cursor");

    public GameFrame() {
        this.setSettings();
    }

    public void setSettings() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle(Minecraft2D.TITLE);
        this.setSize(WIDTH, HEIGHT);
        this.setLocationRelativeTo(null);
        //this.setResizable(false);
    }
}
