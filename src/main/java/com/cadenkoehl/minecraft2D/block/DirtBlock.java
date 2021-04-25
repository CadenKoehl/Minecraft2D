package com.cadenkoehl.minecraft2D.block;

import com.cadenkoehl.minecraft2D.physics.Vec2d;
import com.cadenkoehl.minecraft2D.world.World;

public class DirtBlock extends Block {

    public DirtBlock(Vec2d pos, World world) {
        super("Dirt", pos, world);
    }
}
