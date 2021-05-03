package com.cadenkoehl.minecraft2D.block;

import com.cadenkoehl.minecraft2D.physics.Vec2d;
import com.cadenkoehl.minecraft2D.world.World;

public class LeafBlock extends Block {

    public LeafBlock(Vec2d pos, World world) {
        super("Leaf Block", pos, world);
    }

    @Override
    public boolean canCollide() {
        return false;
    }

    @Override
    public int getBreakSpeed() {
        return 1;
    }
}