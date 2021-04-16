package com.cadenkoehl.minecraft2D;

import com.cadenkoehl.minecraft2D.display.DisplayManager;

public class Minecraft2D {

    public static final String TITLE = "Minecraft 2D";

    public static void main(String[] args) {

        DisplayManager displayManager = new DisplayManager();
        displayManager.createDisplay();

    }
}