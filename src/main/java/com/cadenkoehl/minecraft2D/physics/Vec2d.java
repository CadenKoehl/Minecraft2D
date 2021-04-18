package com.cadenkoehl.minecraft2D.physics;

public class Vec2d {

    public static final Vec2d DEFAULT = new Vec2d(0, 0);

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