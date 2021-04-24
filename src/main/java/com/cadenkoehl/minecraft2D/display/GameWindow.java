package com.cadenkoehl.minecraft2D.display;

import com.cadenkoehl.minecraft2D.block.Block;
import com.cadenkoehl.minecraft2D.block.Blocks;
import com.cadenkoehl.minecraft2D.entities.PlayerEntity;
import com.cadenkoehl.minecraft2D.physics.Vec2d;
import com.cadenkoehl.minecraft2D.render.Renderer;
import com.cadenkoehl.minecraft2D.world.Overworld;
import com.cadenkoehl.minecraft2D.world.Sky;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

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

        player = new PlayerEntity("Player", new Vec2d(5, 1), overworld);

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
        for(Block block: overworld.getBlocks()) {
            block.tick();
        }
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
            player.render();
            overworld.render();
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

                Vec2d pos = Vec2d.toGamePos(new Vec2d(e.getX(), e.getY()));

                switch (e.getButton()) {
                    case MouseEvent.BUTTON3 -> player.placeBlock(Blocks.DIRT, pos);
                    case MouseEvent.BUTTON1 -> player.breakBlock(pos);
                }
            }
        });
    }
}
