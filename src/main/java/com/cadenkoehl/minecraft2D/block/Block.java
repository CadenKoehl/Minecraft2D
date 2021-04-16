package com.cadenkoehl.minecraft2D.block;

import com.cadenkoehl.minecraft2D.display.GamePanel;
import com.cadenkoehl.minecraft2D.physics.Location;
import com.cadenkoehl.minecraft2D.render.Texture;

import javax.swing.*;
import java.awt.*;

public class Block {

    public static final int BLOCK_SIZE_MULTIPLIER = 3;

    private final Texture texture;
    private final String name;
    private boolean isVisible;

    protected Block(String name) {
        this.name = name;
        this.texture = new Texture("textures/blocks/" + this.getName() + ".png");
        this.isVisible = true;
    }

    public Texture getTexture() {
        return this.texture;
    }

    public int getWidth() {
        return this.getTexture().getIcon().getIconWidth() * BLOCK_SIZE_MULTIPLIER;
    }

    public int getHeight() {
        return this.getTexture().getIcon().getIconHeight() * BLOCK_SIZE_MULTIPLIER;
    }

    public void render(Location location) {
        ImageIcon icon = this.getTexture().getIcon();
        Image image = icon.getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon finalIcon = new ImageIcon(image);
        finalIcon.paintIcon(GamePanel.INSTANCE, GamePanel.GRAPHICS, location.getX(), location.getY());
    }

    public void render(int x, int y) {
        render(new Location(x, y));
    }

    public String getDisplayName() {
        return this.name;
    }

    public String getName() {
        return this.name.toLowerCase().replace(" ", "_");
    }

    /**
     * Changes the block's visibility, for example, air blocks are invisible
     */
    public void setVisible(boolean visible) {
        this.isVisible = visible;
    }

}