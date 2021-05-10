package com.cadenkoehl.minecraft2D.client;

import com.cadenkoehl.minecraft2D.display.*;
import com.cadenkoehl.minecraft2D.item.crafting.Recipes;
import com.cadenkoehl.minecraft2D.render.Renderer;
import com.cadenkoehl.minecraft2D.render.Texture;
import com.cadenkoehl.minecraft2D.server.Minecraft2DServer;
import com.cadenkoehl.minecraft2D.util.LogLevel;
import com.cadenkoehl.minecraft2D.util.Logger;
import com.cadenkoehl.minecraft2D.world.Realm;

public class Minecraft2D extends GameClient {

    public static final String TITLE = "Minecraft 2D";
    public static final Texture LOGO = new Texture("textures/logo.png", -10);

    @Override
    public void init() {
        Thread.currentThread().setName("Initialization thread");

        Recipes.registerRecipes();

        state = GameState.GAME;

        realm = new Realm("New World 2", System.nanoTime());
        player = realm.getPlayer();
        overworld = realm.getOverworld();
        nether = realm.getNether();

        hud = new Hud(player);
        new Thread(this::startGameLoop, "Game thread").start();
    }

    @Override
    public void tick() {
        currentWorld = player.getWorld();
        Renderer.CAMERA.centerOn(player);
        currentWorld.tick();
        if(!player.isAlive()) {
            state = GameState.DEATH_SCREEN;
        }
    }

    @Override
    public void stop() {

        if(state == GameState.TITLE_SCREEN) return;

        Logger.log(LogLevel.INFO, "Saving chunks...");
        realm.save();
        Logger.log(LogLevel.INFO, "Shutting down!");
    }

    public static void main(String[] args) {
        if(args.length == 0 || args[0].equals("client")) {
            new Minecraft2D();
        }
        else if(args[0].equals("server")) {
            new Minecraft2DServer();
        }
        else throw new IllegalArgumentException("\"" + args[0] + "\" is not a valid argument!");
    }
}