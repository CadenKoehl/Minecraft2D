package com.cadenkoehl.minecraft2D.block;

import com.cadenkoehl.minecraft2D.physics.Vec2d;
import com.cadenkoehl.minecraft2D.world.World;

public class GrassBlock extends Block {

    public GrassBlock(Vec2d pos, World world) {
        super(pos, world);
    }

    @Override
    public String getDisplayName() {
        return "Grass Block";
    }
}
