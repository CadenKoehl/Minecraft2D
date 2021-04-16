package com.cadenkoehl.minecraft2D.render;

import com.cadenkoehl.minecraft2D.block.Block;
import com.cadenkoehl.minecraft2D.display.GamePanel;
import com.cadenkoehl.minecraft2D.entities.Entity;
import com.cadenkoehl.minecraft2D.physics.Location;
import com.cadenkoehl.minecraft2D.world.World;

import javax.swing.*;
import java.awt.*;

public class Renderer {

    public void render(Block block, int x, int y) {
        block.render(x, y);
    }

    public void render(Entity entity, int x, int y) {
        ImageIcon icon = entity.getTexture().getIcon();
        Image scaledImage = icon.getImage().getScaledInstance(entity.getWidth(), entity.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon finalIcon = new ImageIcon(scaledImage);

        finalIcon.paintIcon(GamePanel.INSTANCE, GamePanel.GRAPHICS, x, y);
    }


    public void renderTerrain(World world) {
        for(Location location : world.blocks.keySet()) {
            Block block = world.blocks.get(location);
            block.render(location);
            GamePanel.INSTANCE.repaint(new Rectangle(location.getX(), location.getY(), block.getWidth(), block.getHeight()));
        }
    }
}
