package com.cadenkoehl.minecraft2D.block;

import com.cadenkoehl.minecraft2D.physics.Vec2d;
import com.cadenkoehl.minecraft2D.world.World;

public class ObsidianBlock extends Block {

    public ObsidianBlock(Vec2d pos, World world) {
        super("Obsidian", pos, world);
    }

    @Override
    public boolean canBeMined() {
        return false;
    }
}