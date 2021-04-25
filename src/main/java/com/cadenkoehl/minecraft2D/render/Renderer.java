package com.cadenkoehl.minecraft2D.render;

import com.cadenkoehl.minecraft2D.display.GameWindow;
import com.cadenkoehl.minecraft2D.entities.Tile;
import com.cadenkoehl.minecraft2D.entities.mob.LivingEntity;
import com.cadenkoehl.minecraft2D.entities.mob.PlayerEntity;

import javax.swing.*;
import java.awt.*;

import static com.cadenkoehl.minecraft2D.display.GameWindow.GRAPHICS;

public class Renderer {

    public static final Camera CAMERA = new Camera();

    public static void render(Tile tile, int x, int y) {
        ImageIcon icon = tile.getTexture().getIcon();

        icon.paintIcon(GameWindow.INSTANCE, GRAPHICS, x - CAMERA.offset.x, y - CAMERA.offset.y);

        if(tile instanceof LivingEntity && !(tile instanceof PlayerEntity)) {
            GRAPHICS.drawString("HP: " + ((LivingEntity) tile).health, x - CAMERA.offset.x, (y - CAMERA.offset.y) - 16);
        }
    }

    public static void render(Texture texture, int x, int y) {
        texture.getIcon().paintIcon(GameWindow.INSTANCE, GRAPHICS, x, y);
    }

    public static void repaint() {
        GameWindow.INSTANCE.repaint();
    }
    public static void repaint(Rectangle rectangle) {
        GameWindow.INSTANCE.repaint(rectangle);
    }
}