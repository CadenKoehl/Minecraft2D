package com.cadenkoehl.minecraft2D.physics;

import com.cadenkoehl.minecraft2D.entities.Tile;

public class Vec2d {

    public static final Vec2d DEFAULT = new Vec2d(0, 0);

    public static Vec2d toScreenPos(Vec2d gamePos) {
        return new Vec2d(gamePos.x * Tile.BLOCK_SIZE, gamePos.y * Tile.BLOCK_SIZE);
    }

    public static Vec2d toGamePos(Vec2d screenPos) {
        return new Vec2d(screenPos.x / Tile.BLOCK_SIZE, screenPos.y / Tile.BLOCK_SIZE);
    }

    public int x;
    public int y;

    public Vec2d(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Vec2d{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}