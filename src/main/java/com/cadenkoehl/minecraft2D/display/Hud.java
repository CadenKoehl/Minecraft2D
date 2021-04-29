package com.cadenkoehl.minecraft2D.display;

import com.cadenkoehl.minecraft2D.entities.player.PlayerEntity;
import com.cadenkoehl.minecraft2D.render.Renderer;
import com.cadenkoehl.minecraft2D.render.Texture;

import static com.cadenkoehl.minecraft2D.display.GameWindow.*;

public class Hud {

    public static final Texture HEART = new Texture("textures/gui/heart.png");
    public static boolean f3 = false;

    private final PlayerEntity player;

    public Hud(PlayerEntity player) {
        this.player = player;
    }

    public void update() {
        updateHP();
        updateF3();
    }

    private void updateHP() {
        for(int i = 1; i <= player.health; i++) {
            Renderer.render(HEART, i * 20, 10);
        }
    }

    private void updateF3() {
        if(f3) {
            GRAPHICS.drawString("fps: " + fps, 25, 50);
            GRAPHICS.drawString("x: " + player.pos.x, 25, 70);
            GRAPHICS.drawString("y: " + ((-player.pos.y) + 10), 25, 90);
            GRAPHICS.drawString("blocks: " + overworld.getBlocks().size(), 25, 110);
            GRAPHICS.drawString("entities: " + (overworld.getEntities().size() + 1), 25, 130);
            GRAPHICS.drawString("day " + (overworld.days), 25, 150);
        }
    }
}