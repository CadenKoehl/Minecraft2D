package com.cadenkoehl.minecraft2D.render;

import javax.swing.*;
import java.io.File;

public class Texture {

    private final ImageIcon icon;
    private final File file;
    private final String path;

    public Texture(String filePath) {
        this.path = filePath;
        this.file = new File(filePath);
        this.icon = new ImageIcon(filePath);
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
