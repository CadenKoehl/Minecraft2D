package com.cadenkoehl.minecraft2D.render;

import com.cadenkoehl.minecraft2D.display.GameWindow;
import com.cadenkoehl.minecraft2D.entities.Tile;
import com.cadenkoehl.minecraft2D.entities.Entity;
import com.cadenkoehl.minecraft2D.entities.player.PlayerEntity;

import javax.swing.*;

import java.awt.*;

import static com.cadenkoehl.minecraft2D.display.GameWindow.GRAPHICS;

public class Renderer {

    public static final Camera CAMERA = new Camera();

    public static void render(Tile tile, int x, int y) {
        ImageIcon icon = tile.getTexture().getIcon();

        if(icon == null) {
            Color oldColor = GRAPHICS.getColor();
            GRAPHICS.setColor(tile.getTexture().getColor());
            GRAPHICS.fillRect(x - CAMERA.offset.x, y - CAMERA.offset.y, tile.getTexture().getWidth(), tile.getTexture().getHeight());
            GRAPHICS.setColor(oldColor);
        }
        else {
            icon.paintIcon(GameWindow.INSTANCE, GRAPHICS, x - CAMERA.offset.x, y - CAMERA.offset.y);
        }

        if (tile instanceof Entity && !(tile instanceof PlayerEntity)) {
            GRAPHICS.drawString("HP: " + ((Entity) tile).health, x - CAMERA.offset.x, (y - CAMERA.offset.y) - 16);
        }
    }

    public static void render(Texture texture, int x, int y) {
        if(texture.getIcon() == null) {
            Color oldColor = GRAPHICS.getColor();
            GRAPHICS.setColor(texture.getColor());
            GRAPHICS.fillRect(x, y, texture.getWidth(), texture.getHeight());
            GRAPHICS.setColor(oldColor);
        }
        else {
            texture.getIcon().paintIcon(GameWindow.INSTANCE, GRAPHICS, x, y);
        }
    }
}