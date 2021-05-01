package com.cadenkoehl.minecraft2D.render;

import com.cadenkoehl.minecraft2D.block.Block;
import com.cadenkoehl.minecraft2D.entities.Tile;
import com.cadenkoehl.minecraft2D.util.LogLevel;
import com.cadenkoehl.minecraft2D.util.Logger;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.MissingResourceException;

public class Texture {

    private ImageIcon icon;
    private Color color;
    private File file;
    private String path;
    private int width;
    private int height;

    public Texture(String filePath, int sizeMultiplier) {
        this.path = filePath;
        this.file = new File(filePath);
        this.icon = this.createIcon(filePath, sizeMultiplier);
        this.height = icon.getIconHeight();
        this.width = icon.getIconWidth();
    }

    public Texture(Color color, int height, int width) {
        this.color = color;
        this.height = height;
        this.width = width;
    }

    public Texture(String filePath) {
        this(filePath, Block.SIZE_MULTIPLIER);
    }

    private ImageIcon createIcon(String filePath, int sizeMultiplier) {
        ImageIcon icon = new ImageIcon(filePath);

        Image scaledImage = icon.getImage().getScaledInstance(icon.getIconWidth() * sizeMultiplier, icon.getIconHeight() * sizeMultiplier, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    public Texture rescale(int newSize) {
        if(this.icon == null) {
            this.height = newSize;
            this.width = newSize;
            return this;
        }
        this.icon = new ImageIcon(icon.getImage().getScaledInstance(newSize, newSize, Image.SCALE_SMOOTH));
        return this;
    }

    public Color getColor() {
        return color;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public ImageIcon getIcon() {
        return this.icon;
    }

    public File getFile() {
        return this.file;
    }

    public String getPath() {
        return this.path;
    }
}
