package com.cadenkoehl.minecraft2D.display;

import com.cadenkoehl.minecraft2D.entities.PlayerEntity;
import com.cadenkoehl.minecraft2D.render.Renderer;
import com.cadenkoehl.minecraft2D.world.Overworld;
import com.cadenkoehl.minecraft2D.world.Sky;
import com.cadenkoehl.minecraft2D.world.gen.TerrainGenerator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GamePanel extends JPanel {

    public static GamePanel INSTANCE = null;
    public static Graphics GRAPHICS = null;
    public static final Overworld OVERWORLD = new Overworld();
    public static PlayerEntity PLAYER = null;
    private final GameFrame FRAME;
    private int i = 0;

    public GamePanel(GameFrame frame) {
        this.FRAME = frame;
        INSTANCE = this;
        this.setBackground(Sky.COLOR);
        this.setFocusable(true);

        PLAYER = new PlayerEntity(100, 100, OVERWORLD);

        setUpKeys();

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        GRAPHICS = g;
        GameFrame.HEIGHT = FRAME.getHeight();
        GameFrame.WIDTH = FRAME.getWidth();

        TerrainGenerator terrain = new TerrainGenerator(OVERWORLD);
        terrain.generate();

        Renderer renderer = new Renderer();
        renderer.renderTerrain(OVERWORLD);

        GamePanel.PLAYER.render();

        if(i == 0) {
            repaint();
        }

        i++;

    }

    public void setUpKeys() {
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_A -> PLAYER.direction = 'L';
                    case KeyEvent.VK_D -> PLAYER.direction = 'R';
                }
            }
        });
    }
}
