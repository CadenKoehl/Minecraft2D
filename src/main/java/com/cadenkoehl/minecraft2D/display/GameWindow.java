package com.cadenkoehl.minecraft2D.display;

import com.cadenkoehl.minecraft2D.block.Block;
import com.cadenkoehl.minecraft2D.entities.PlayerEntity;
import com.cadenkoehl.minecraft2D.render.Renderer;
import com.cadenkoehl.minecraft2D.world.Overworld;
import com.cadenkoehl.minecraft2D.world.Sky;
import com.cadenkoehl.minecraft2D.world.gen.TerrainGenerator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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

        player = new PlayerEntity(100, 100, overworld);

        TerrainGenerator generator = new TerrainGenerator(overworld);
        generator.generate();

        setUpKeys();

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
    }

    public void tick() {
        player.tick();
        for (Block block: overworld.getBlocks()) {
            block.tick();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        GRAPHICS = g;
        GameFrame.HEIGHT = FRAME.getHeight();
        GameFrame.WIDTH = FRAME.getWidth();

        player.render();
        Renderer.renderTerrain(overworld);
    }

    public void setUpKeys() {
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_A -> player.setVelocityX(-1);
                    case KeyEvent.VK_D -> player.setVelocityX(1);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_A, KeyEvent.VK_D -> player.setVelocityX(0);
                }
            }
        });
    }
}
