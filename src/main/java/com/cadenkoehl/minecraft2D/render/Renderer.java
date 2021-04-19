package com.cadenkoehl.minecraft2D.render;

import com.cadenkoehl.minecraft2D.block.Block;
import com.cadenkoehl.minecraft2D.display.GameWindow;
import com.cadenkoehl.minecraft2D.entities.LivingEntity;
import com.cadenkoehl.minecraft2D.entities.PlayerEntity;
import com.cadenkoehl.minecraft2D.entities.Tile;
import com.cadenkoehl.minecraft2D.physics.Vec2d;
import com.cadenkoehl.minecraft2D.world.World;

import javax.swing.*;
import java.awt.*;

public class Renderer {

    public static void render(Tile tile, int x, int y) {
        ImageIcon icon = tile.getTexture().getIcon();
        icon.paintIcon(GameWindow.INSTANCE, GameWindow.GRAPHICS, x, y);
    }


    public static void renderTerrain(World world) {
        for(Block block : world.getBlocks()) {
            block.render();
        }
    }
}