package com.cadenkoehl.minecraft2D.block;

import com.cadenkoehl.minecraft2D.physics.Vec2d;
import com.cadenkoehl.minecraft2D.world.World;

public class NetherPortalBlock extends Block {

    public NetherPortalBlock(String displayName, Vec2d pos, World world) {
        super(displayName, pos, world);
    }

    @Override
    public boolean canCollide() {
        return false;
    }

    @Override
    public boolean canBeMined() {
        return false;
    }
}