package com.cadenkoehl.minecraft2D;

import com.cadenkoehl.minecraft2D.display.*;
import com.cadenkoehl.minecraft2D.entities.player.PlayerEntity;
import com.cadenkoehl.minecraft2D.physics.Vec2d;
import com.cadenkoehl.minecraft2D.world.Nether;
import com.cadenkoehl.minecraft2D.world.Overworld;
import com.cadenkoehl.minecraft2D.world.World;

import java.awt.*;
import java.util.ConcurrentModificationException;
import java.util.Timer;
import java.util.TimerTask;

public abstract class GameClient {

    private static GameClient INSTANCE;

    private final GameFrame frame;
    private final GameWindow window;

    public World currentWorld;
    public Overworld overworld;
    public Nether nether;
    public PlayerEntity player = null;
    public Hud hud;
    public int fps = 60;
    public int delay = 2;
    private int fpsCounter;
    public final TitleScreen titleScreen;
    public final Input inputManager;
    public GameState state = GameState.TITLE_SCREEN;

    public GameClient() {

        Runtime.getRuntime().addShutdownHook(new Thread(this::stop, "Shutdown thread"));

        this.frame = new GameFrame();
        this.window = new GameWindow(this, frame);
        frame.add(window);
        frame.setVisible(true);
        MenuButton singleplayer = new MenuButton("Singleplayer", GameFrame.WIDTH / 3, GameFrame.HEIGHT / 3, 300, 100);
        singleplayer.onClick(this::init);
        MenuButton multiplayer = new MenuButton("Multiplayer", GameFrame.WIDTH / 3, (GameFrame.HEIGHT / 3) + 100, 300, 100);

        titleScreen = new TitleScreen();
        titleScreen.add(singleplayer);
        titleScreen.add(multiplayer);
        this.inputManager = new Input(this);
        scheduleFPSTimer();
        INSTANCE = this;
    }

    public static GameClient getInstance() {
        return INSTANCE;
    }

    public static PlayerEntity getPlayer() {
        return INSTANCE.player;
    }

    public World getCurrentWorld() {
        return INSTANCE.currentWorld;
    }

    public static World getOverworld() {
        return INSTANCE.overworld;
    }

    public static Nether getNether() {
        return INSTANCE.nether;
    }

    public static int getFPS() {
        return INSTANCE.fps;
    }

    public static GameState getState() {
        return INSTANCE.state;
    }

    public static Vec2d getMousePos() {
        return INSTANCE.getMousePosition();
    }

    public void renderFrame(Graphics g) {
        fpsCounter++;

        if(state == GameState.TITLE_SCREEN) {
            window.setBackground(Color.BLACK);
            titleScreen.render();
        }

        if(state == GameState.GAME) {
            window.setBackground(currentWorld.skyColor);
            currentWorld.render();
            player.render();
            hud.update();

            if(player.portalTicks > 0) {
                Color oldColor = g.getColor();
                g.setColor(new Color(136, 0, 255, 100));
                g.fillRect(0, 0, GameFrame.WIDTH, GameFrame.HEIGHT);
                g.setColor(oldColor);
            }
        }

        if(state == GameState.DEATH_SCREEN) {
            window.setBackground(new Color(150, 212, 246));
            g.setFont(new Font("Courier", Font.BOLD, 60));
            g.drawString("Game Over", GameFrame.WIDTH / 2 - 165, GameFrame.HEIGHT / 2 - 30);
        }
    }

    public Vec2d getMousePosition() {
        return inputManager.getMousePosition();
    }

    public void startGameLoop() {
        while(state == GameState.GAME) {
            try {
                this.tick();
            }
            catch(ConcurrentModificationException ex) {
                //empty catch block
            }
            frame.repaint();
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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

    public GameFrame getFrame() {
        return frame;
    }

    public GameWindow getWindow() {
        return window;
    }

    public abstract void init();
    public abstract void tick();
    public abstract void stop();
}