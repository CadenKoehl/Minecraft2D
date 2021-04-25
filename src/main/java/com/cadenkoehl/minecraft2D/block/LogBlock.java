package com.cadenkoehl.minecraft2D.block;

import com.cadenkoehl.minecraft2D.physics.Vec2d;
import com.cadenkoehl.minecraft2D.world.World;

public class LogBlock extends Block {

    public LogBlock(Vec2d pos, World world) {
        super("Log", pos, world);
    }
}
