package com.cadenkoehl.minecraft2D.world;

import com.cadenkoehl.minecraft2D.physics.Vec2d;

import java.awt.*;

import static com.cadenkoehl.minecraft2D.display.GameWindow.GRAPHICS;

public class Sun {

    public Vec2d pos;
    public int width;
    public int height;

    public Sun(int x, int y, int width, int height) {
        this.pos = new Vec2d(x, y);
        this.width = width;
        this.height = height;
    }

    public void render() {
        GRAPHICS.setColor(Color.WHITE);
        GRAPHICS.fillRect(pos.x, pos.y, width, height);
        GRAPHICS.setColor(Color.BLACK);
    }
}
