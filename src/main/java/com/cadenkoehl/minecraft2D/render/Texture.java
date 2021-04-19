package com.cadenkoehl.minecraft2D.render;

import com.cadenkoehl.minecraft2D.entities.Tile;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Texture {

    private final ImageIcon icon;
    private final File file;
    private final String path;

    public Texture(String filePath) {
        this.path = "Users/cadenkoehl/IntelliJ/Java/Minecraft2D/" + filePath;
        this.file = new File(filePath);
        this.icon = this.createIcon(filePath);
    }

    private ImageIcon createIcon(String filePath) {
        ImageIcon icon = new ImageIcon(filePath);

        Image scaledImage = icon.getImage().getScaledInstance(icon.getIconWidth() * Tile.SIZE_MULTIPLIER, icon.getIconHeight() * Tile.SIZE_MULTIPLIER, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
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
