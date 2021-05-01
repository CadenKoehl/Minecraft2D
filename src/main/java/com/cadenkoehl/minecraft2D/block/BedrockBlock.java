package com.cadenkoehl.minecraft2D.block;

import com.cadenkoehl.minecraft2D.physics.Vec2d;
import com.cadenkoehl.minecraft2D.world.World;

public class BedrockBlock extends Block {

    public BedrockBlock(Vec2d pos, World world) {
        super("Bedrock", pos, world);
    }

    @Override
    public boolean canBeMined() {
        return false;
    }
}
