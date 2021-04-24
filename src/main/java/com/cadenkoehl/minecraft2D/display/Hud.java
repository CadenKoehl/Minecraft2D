package com.cadenkoehl.minecraft2D.display;

import com.cadenkoehl.minecraft2D.entities.PlayerEntity;
import com.cadenkoehl.minecraft2D.render.Renderer;
import com.cadenkoehl.minecraft2D.render.Texture;

import java.awt.*;

public class Hud {

    public static final Texture HEART = new Texture("textures/gui/heart.png");

    private final PlayerEntity player;

    public Hud(PlayerEntity player) {
        this.player = player;
    }

    public void updateHP() {
        for(int i = 1; i < player.health; i++) {
            Renderer.render(HEART, i * 20, 10);
            Renderer.repaint(new Rectangle(i * 20, 10, HEART.getIcon().getIconWidth(), HEART.getIcon().getIconHeight()));
        }
    }
}