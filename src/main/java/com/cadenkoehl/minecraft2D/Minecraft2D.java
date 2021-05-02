package com.cadenkoehl.minecraft2D;

import com.cadenkoehl.minecraft2D.display.*;
import com.cadenkoehl.minecraft2D.entities.player.PlayerEntity;
import com.cadenkoehl.minecraft2D.item.crafting.Recipes;
import com.cadenkoehl.minecraft2D.physics.Vec2d;
import com.cadenkoehl.minecraft2D.render.Renderer;
import com.cadenkoehl.minecraft2D.render.Texture;
import com.cadenkoehl.minecraft2D.util.LogLevel;
import com.cadenkoehl.minecraft2D.util.Logger;
import com.cadenkoehl.minecraft2D.world.Overworld;

public class Minecraft2D extends Game {

    public static final String TITLE = "Minecraft 2D";
    public static final Texture LOGO = new Texture("textures/logo.png", -10);

    @Override
    public void init() {
        Thread.currentThread().setName("Initialization thread");

        Recipes.registerRecipes();

        state = GameState.GAME;

        overworld = new Overworld(System.nanoTime());
        currentWorld = overworld;

        player = new PlayerEntity("Player", new Vec2d(17, 4), currentWorld);

        hud = new Hud(player);
        new Thread(Minecraft2D.this::startGameLoop, "Game thread").start();
    }

    @Override
    public void tick() {
        player.tick();
        currentWorld = player.getWorld();
        Renderer.CAMERA.centerOn(player);
        currentWorld.tick();
        if(!player.isAlive()) {
            state = GameState.DEATH_SCREEN;
        }
    }

    @Override
    public void stop() {
        Logger.log(LogLevel.INFO, "Shutting down!");
    }
}