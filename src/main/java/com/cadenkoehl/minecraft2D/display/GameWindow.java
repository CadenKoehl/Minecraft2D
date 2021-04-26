package com.cadenkoehl.minecraft2D.display;

import com.cadenkoehl.minecraft2D.entities.Tile;
import com.cadenkoehl.minecraft2D.entities.mob.LivingEntity;
import com.cadenkoehl.minecraft2D.entities.player.PlayerEntity;
import com.cadenkoehl.minecraft2D.entities.mob.hostile.ZombieEntity;
import com.cadenkoehl.minecraft2D.physics.Vec2d;
import com.cadenkoehl.minecraft2D.render.Renderer;
import com.cadenkoehl.minecraft2D.world.Overworld;
import com.cadenkoehl.minecraft2D.world.Sky;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;

public class GameWindow extends JPanel {

    public static GameWindow INSTANCE = null;
    public static Graphics GRAPHICS = null;
    public static final Overworld overworld = new Overworld();
    public static PlayerEntity player = null;
    public static boolean readyForNextFrame = true;
    public static Hud hud;
    public static int fps = 60;
    public static int delay = 2;
    private static int fpsCounter;
    private final GameFrame FRAME;
    private boolean isRunning;

    public GameWindow(GameFrame frame) {
        Thread.currentThread().setName("Render thread");
        this.FRAME = frame;
        INSTANCE = this;
        this.setBackground(Sky.COLOR);
        this.setFocusable(true);

        player = new PlayerEntity("Player", new Vec2d(10, 1), overworld);

        hud = new Hud(player);

        setUpInput();

        isRunning = true;

        scheduleFPSTimer();

        new Thread(this::startGameLoop, "Game thread").start();
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
        while(this.isRunning) {
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
            isRunning = false;
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        GRAPHICS = g;
        GameFrame.HEIGHT = FRAME.getHeight();
        GameFrame.WIDTH = FRAME.getWidth();

        fpsCounter++;

        if(isRunning) {
            overworld.render();
            player.render();
            hud.update();
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
                    case MouseEvent.BUTTON1 -> {
                        Tile entity = overworld.getEntity(pos);

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
