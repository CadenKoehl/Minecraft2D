package com.cadenkoehl.minecraft2D.block;

import com.cadenkoehl.minecraft2D.physics.Vec2d;
import com.cadenkoehl.minecraft2D.world.World;

public class BackgroundStoneBlock extends Block {

    public BackgroundStoneBlock(Vec2d pos, World world) {
        super("Background Stone", pos, world);
    }

    @Override
    public boolean canBeMined() {
        return false;
    }
}
