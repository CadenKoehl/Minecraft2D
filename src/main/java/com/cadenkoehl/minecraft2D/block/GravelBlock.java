package com.cadenkoehl.minecraft2D.block;

import com.cadenkoehl.minecraft2D.physics.Vec2d;
import com.cadenkoehl.minecraft2D.world.World;

public class GravelBlock extends Block {

    public GravelBlock(Vec2d pos, World world) {
        super("Gravel", pos, world);
    }
}
