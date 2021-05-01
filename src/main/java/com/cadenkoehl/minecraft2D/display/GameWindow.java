package com.cadenkoehl.minecraft2D.display;

import com.cadenkoehl.minecraft2D.Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;

public class GameWindow extends JPanel {

    public static GameWindow INSTANCE = null;
    public static Graphics GRAPHICS = null;
    private final GameFrame FRAME;
    private final Game game;
    public static boolean readyForNextFrame;

    public GameWindow(Game game, GameFrame frame) {
        Thread.currentThread().setName("Render thread");
        this.FRAME = frame;
        this.game = game;
        INSTANCE = this;
        this.setFocusable(true);
        readyForNextFrame = true;
        setUpInput();
    }

    public void takeScreenshot() {
        BufferedImage image = this.getGraphicsConfiguration().createCompatibleImage(this.getWidth(), this.getHeight());
        this.paint(image.getGraphics());
        File file = new File(new Date() + ".png");
        try {
            ImageIO.write(image, "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        GRAPHICS = g;
        GameFrame.HEIGHT = FRAME.getHeight();
        GameFrame.WIDTH = FRAME.getWidth();
        game.renderFrame(g);
    }

    public void setUpInput() {
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                game.inputManager.onKeyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                game.inputManager.onKeyReleased(e);
            }
        });

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                game.inputManager.onMouseClicked(e);
            }
        });
    }
}
