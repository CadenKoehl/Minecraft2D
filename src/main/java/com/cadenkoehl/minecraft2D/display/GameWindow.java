package com.cadenkoehl.minecraft2D.display;

import com.cadenkoehl.minecraft2D.Game;
import com.cadenkoehl.minecraft2D.GameState;
import com.cadenkoehl.minecraft2D.Minecraft2D;
import com.cadenkoehl.minecraft2D.entities.Tile;
import com.cadenkoehl.minecraft2D.entities.mob.LivingEntity;
import com.cadenkoehl.minecraft2D.entities.player.PlayerEntity;
import com.cadenkoehl.minecraft2D.entities.mob.hostile.ZombieEntity;
import com.cadenkoehl.minecraft2D.physics.Vec2d;
import com.cadenkoehl.minecraft2D.render.Renderer;
import com.cadenkoehl.minecraft2D.util.TimeUtil;
import com.cadenkoehl.minecraft2D.world.Overworld;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class GameWindow extends JPanel {

    public static GameWindow INSTANCE = null;
    public static Graphics GRAPHICS = null;
    public static Overworld overworld;
    public static PlayerEntity player = null;
    public static boolean readyForNextFrame = true;
    public static Hud hud;
    public static int fps = 60;
    public static int delay = 2;
    private static int fpsCounter;
    private final GameFrame FRAME;
    private final TitleScreen titleScreen;
    public static GameState state = GameState.TITLE_SCREEN;
    private final Game game;

    public GameWindow(Game game, GameFrame frame) {
        Thread.currentThread().setName("Render thread");
        this.FRAME = frame;
        this.game = game;
        INSTANCE = this;
        this.setFocusable(true);

        MenuButton singleplayer = new MenuButton("Singleplayer", GameFrame.WIDTH / 3, GameFrame.HEIGHT / 3, 300, 100);
        singleplayer.onClick(this::initGame);

        titleScreen = new TitleScreen();
        titleScreen.add(singleplayer);

        setUpInput();
    }

    public void initGame() {

        Thread.currentThread().setName("Initialization thread");

        state = GameState.GAME;

        overworld = new Overworld();

        player = new PlayerEntity("Player", new Vec2d(10, 1), overworld);

        hud = new Hud(player);
        scheduleFPSTimer();
        new Thread(GameWindow.this::startGameLoop, "Game thread").start();
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

    private void scheduleFPSTimer() {
        Timer fpsTimer = new Timer();
        fpsTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                fps = fpsCounter;
                fpsCounter = 0;
                scheduleFPSTimer();
            }
        }, 1000);
    }

    public void startGameLoop() {
        while(state == GameState.GAME) {
            readyForNextFrame = false;
            this.tick();
            try {
                Thread.sleep(2);
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
            state = GameState.DEATH_SCREEN;
        }
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        GRAPHICS = g;
        GameFrame.HEIGHT = FRAME.getHeight();
        GameFrame.WIDTH = FRAME.getWidth();

        fpsCounter++;

        if(state == GameState.TITLE_SCREEN) {
            this.setBackground(Color.BLACK);
            titleScreen.render();
        }

        if(state == GameState.GAME) {
            this.setBackground(overworld.skyColor);
            overworld.render();
            player.render();
            hud.update();
        }

        if(state == GameState.DEATH_SCREEN) {
            this.setBackground(new Color(150, 212, 246));
            g.setFont(new Font("Courier", Font.BOLD, 60));
            g.drawString("Game Over", GameFrame.WIDTH / 2 - 165, GameFrame.HEIGHT / 2 - 30);
        }
    }

    public void setUpInput() {
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

                if(state != GameState.GAME) return;

                switch (e.getKeyCode()) {
                    case KeyEvent.VK_A -> {
                        if(e.isAltDown()) player.setVelocityX(-2);
                        else player.setVelocityX(-1);
                    }
                    case KeyEvent.VK_D -> {
                        if(e.isAltDown()) player.setVelocityX(2);
                        else player.setVelocityX(1);
                    }
                    case KeyEvent.VK_SPACE -> player.jump();
                    case KeyEvent.VK_K -> player.damage(2);
                    case KeyEvent.VK_Z -> overworld.spawnEntity(new ZombieEntity(new Vec2d(player.pos.x, 1), overworld));
                    case KeyEvent.VK_T -> overworld.generator.nextChunk();
                    case KeyEvent.VK_F3 -> Hud.f3 = !Hud.f3;
                    case KeyEvent.VK_F2 -> takeScreenshot();
                    case KeyEvent.VK_ESCAPE -> {
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

                if(state != GameState.GAME) return;

                switch (e.getKeyCode()) {
                    case KeyEvent.VK_A, KeyEvent.VK_D -> player.setVelocityX(0);
                }
            }
        });
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if(state != GameState.GAME) return;

                Vec2d pos = Vec2d.toGamePos(new Vec2d(e.getX() + Renderer.CAMERA.offset.x, e.getY() + Renderer.CAMERA.offset.y));

                switch (e.getButton()) {
                    case MouseEvent.BUTTON3 -> player.placeBlock(pos);
                    case MouseEvent.BUTTON1 -> {
                        Tile entity = overworld.getEntity(pos);

                        if (entity == null) {
                            entity = overworld.getEntity(new Vec2d(pos.x - 1, pos.y));
                        }

                        if (entity instanceof LivingEntity) {
                            player.tryAttack((LivingEntity) entity);
                            return;
                        }
                        player.breakBlock(pos);
                    }
                }
            }
        });
    }
}
