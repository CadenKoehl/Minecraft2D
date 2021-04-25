package com.cadenkoehl.minecraft2D.block;

import com.cadenkoehl.minecraft2D.physics.Vec2d;
import com.cadenkoehl.minecraft2D.world.World;

public class StoneBlock extends Block {

    public StoneBlock(Vec2d pos, World world) {
        super("Stone", pos, world);
    }
}