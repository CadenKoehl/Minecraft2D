package com.cadenkoehl.minecraft2D.display;

import com.cadenkoehl.minecraft2D.Minecraft2D;
import com.cadenkoehl.minecraft2D.block.Block;
import com.cadenkoehl.minecraft2D.block.Blocks;
import com.cadenkoehl.minecraft2D.entities.PlayerEntity;
import com.cadenkoehl.minecraft2D.entities.Tile;
import com.cadenkoehl.minecraft2D.physics.Vec2d;
import com.cadenkoehl.minecraft2D.render.Renderer;
import com.cadenkoehl.minecraft2D.world.Overworld;
import com.cadenkoehl.minecraft2D.world.Sky;
import com.cadenkoehl.minecraft2D.world.gen.TerrainGenerator;

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

        player = new PlayerEntity("Player", new Vec2d(4, 1), overworld);

        TerrainGenerator generator = new TerrainGenerator(overworld);
        generator.generate();

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

    public void setUpInput() {
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_A -> player.setVelocityX(-1);
                    case KeyEvent.VK_D -> player.setVelocityX(1);
                    case KeyEvent.VK_W -> player.setVelocityY(-1);
                    case KeyEvent.VK_S -> player.setVelocityY(1);
                    //case KeyEvent.VK_SPACE -> player.jump();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_A, KeyEvent.VK_D -> player.setVelocityX(0);
                    case KeyEvent.VK_W, KeyEvent.VK_S -> player.setVelocityY(0);
                }
            }
        });
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                player.placeBlock(Blocks.DIRT, Vec2d.toGamePos(new Vec2d(e.getX(), e.getY())));
            }
        });
    }
}
