package com.cadenkoehl.minecraft2D.display;

import com.cadenkoehl.minecraft2D.block.Block;
import com.cadenkoehl.minecraft2D.block.Blocks;
import com.cadenkoehl.minecraft2D.entities.PlayerEntity;
import com.cadenkoehl.minecraft2D.physics.Vec2d;
import com.cadenkoehl.minecraft2D.render.Renderer;
import com.cadenkoehl.minecraft2D.util.LogLevel;
import com.cadenkoehl.minecraft2D.util.Logger;
import com.cadenkoehl.minecraft2D.world.Overworld;
import com.cadenkoehl.minecraft2D.world.Sky;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ConcurrentModificationException;

public class GameWindow extends JPanel {

    public static GameWindow INSTANCE = null;
    public static Graphics GRAPHICS = null;
    public static final Overworld overworld = new Overworld();
    public static PlayerEntity player = null;
    private final GameFrame FRAME;
    private boolean isRunning;

    public GameWindow(GameFrame frame) {
        Thread.currentThread().setName("Render thread");
        this.FRAME = frame;
        INSTANCE = this;
        this.setBackground(Sky.COLOR);
        this.setFocusable(true);

        player = new PlayerEntity("Player", new Vec2d(10, 1), overworld);

        setUpInput();

        isRunning = true;

        new Thread(this::startGameLoop, "Game thread").start();
    }

    public void startGameLoop() {
        while(this.isRunning) {
            this.tick();
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        repaint();
    }

    public void tick() {
        player.tick();
        Renderer.CAMERA.centerOn(player);
        overworld.tick();
        if(!player.isAlive()) {
            isRunning = false;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        GRAPHICS = g;
        GameFrame.HEIGHT = FRAME.getHeight();
        GameFrame.WIDTH = FRAME.getWidth();

        if(isRunning) {
            overworld.render();
            player.render();
        }
        else {
            g.setFont(new Font("Courier", Font.BOLD, 60));
            g.drawString("Game Over", GameFrame.WIDTH / 2 - 165, GameFrame.HEIGHT / 2 - 30);
        }
    }

    public void setUpInput() {
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_A -> player.setVelocityX(-1);
                    case KeyEvent.VK_D -> player.setVelocityX(1);
                    case KeyEvent.VK_SPACE -> player.jump();
                    case KeyEvent.VK_K -> player.damage(1);
                    case KeyEvent.VK_G -> repaint();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_A, KeyEvent.VK_D -> player.setVelocityX(0);
                }
            }
        });
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                Vec2d pos = Vec2d.toGamePos(new Vec2d(e.getX() + Renderer.CAMERA.offset.x, e.getY() + Renderer.CAMERA.offset.y));

                switch (e.getButton()) {
                    case MouseEvent.BUTTON3 -> player.placeBlock(pos);
                    case MouseEvent.BUTTON1 -> player.breakBlock(pos);
                }
            }
        });
    }
}
