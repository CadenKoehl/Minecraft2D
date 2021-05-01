package com.cadenkoehl.minecraft2D.block;

import com.cadenkoehl.minecraft2D.physics.Vec2d;
import com.cadenkoehl.minecraft2D.world.World;

import java.awt.*;

public class WaterBlock extends FluidBlock {

    public WaterBlock(Vec2d pos, World world) {
        super("Water", pos, world);
    }

    @Override
    public Color getColor() {
        return new Color(0, 123, 255, 70);
    }
}